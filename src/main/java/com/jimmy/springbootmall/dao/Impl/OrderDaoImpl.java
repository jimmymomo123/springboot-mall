package com.jimmy.springbootmall.dao.Impl;

import com.jimmy.springbootmall.dao.OrderDao;
import com.jimmy.springbootmall.dto.OrderQueryParam;
import com.jimmy.springbootmall.model.Order;
import com.jimmy.springbootmall.model.OrderItem;
import com.jimmy.springbootmall.rowmapper.OrderItemRowMapper;
import com.jimmy.springbootmall.rowmapper.OrderRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderDaoImpl implements OrderDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer countOrder(OrderQueryParam orderQueryParam) {
        String sql = "select count(*) from `order` where 1=1";

        Map<String,Object> map = new HashMap<>();

        //查詢條件
        sql = addFilteringSql(sql,map, orderQueryParam);

        Integer total = namedParameterJdbcTemplate.queryForObject(sql, map , Integer.class);

        return total;
    }

    @Override
    public List<Order> getOrders(OrderQueryParam orderQueryParam) {
        String sql = "select order_id, user_id, total_amount, created_date, last_modified_date from  `order` where 1=1";

        Map<String,Object> map = new HashMap<>();

        //查詢條件
        sql = addFilteringSql(sql,map, orderQueryParam);

        //排序
        sql += " order by created_date desc";

        //分頁
        sql += " limit :limit offset :offset";
        map.put("limit", orderQueryParam.getLimit());
        map.put("offset", orderQueryParam.getOffset());

        List<Order> orderList = namedParameterJdbcTemplate.query(sql, map, new OrderRowMapper());

        return orderList;

    }

    @Override
    public Order getOrderById(Integer orderId) {
        String sql = "select order_id, user_id, total_amount, created_date, last_modified_date " +
                "from `order` where order_id = :orderId";

        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);

        List<Order> orderList = namedParameterJdbcTemplate.query(sql, map, new OrderRowMapper());

        if (orderList.size() > 0){
            return orderList.get(0);
        } else {
            return  null;
        }
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(Integer orderId) {
        String sql = "select oi.order_item_id, oi.order_id, oi.product_id, oi.quantity, oi.amount," +
                " p.product_name, p.image_url " +
                "from order_item as oi " +
                "left join product as p on oi.product_id = p.product_id " +
                "where oi.order_id = :orderId";

        Map<String, Object> map = new HashMap<>();
        map.put("orderId",orderId);

        List<OrderItem> orderItemList = namedParameterJdbcTemplate.query(sql, map, new OrderItemRowMapper());

        return orderItemList;
    }

    @Override
    public Integer createOrder(Integer userId, Integer totalAmount) {
        String sql = "insert into `order`(user_id, total_amount, created_date, last_modified_date)" +
                "values (:userId, :totalAmount, :createdDate, :lastModifiedDate)";

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("totalAmount", totalAmount);

        map.put("createdDate", new Date());
        map.put("lastModifiedDate", new Date());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        int orderId = keyHolder.getKey().intValue();

        return orderId;
    }

    @Override
    public void createOrderItems(Integer orderId, List<OrderItem> orderItemList) {

        //使用 for loop 一條一條 sql 加入數據，效率較低
//        for (OrderItem orderItem : orderItemList){
//
//            String sql = "insert into order_item(order_id, product_id, quantity, amount)" +
//                    "values (:orderId, :productId, :quantity, :amount)";
//
//            Map<String, Object> map = new HashMap<>();
//            map.put("orderId", orderId);
//            map.put("productId", orderItem.getProductId());
//            map.put("quantity", orderItem.getQuantity());
//            map.put("amount", orderItem.getAmount());
//
//            namedParameterJdbcTemplate.update(sql, map);
//        }

        //使用 batchUpdate 一次性加入數據，效率更高
        String sql = "insert into order_item(order_id, product_id, quantity, amount)" +
                "values (:orderId, :productId, :quantity, :amount)";

        MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[orderItemList.size()];

        for (int i = 0 ; i < orderItemList.size() ; i++){
            OrderItem orderItem = orderItemList.get(i);

            parameterSources[i] = new MapSqlParameterSource();
            parameterSources[i].addValue("orderId",orderId);
            parameterSources[i].addValue("productId",orderItem.getProductId());
            parameterSources[i].addValue("quantity",orderItem.getQuantity());
            parameterSources[i].addValue("amount",orderItem.getAmount());

        }

        namedParameterJdbcTemplate.batchUpdate(sql, parameterSources);
    }

    private String addFilteringSql(String sql, Map<String,Object> map, OrderQueryParam orderQueryParam){
        if(orderQueryParam.getUserId() != null){
            sql += " and user_id = :userId";
            map.put("userId", orderQueryParam.getUserId());
        }
        return sql;
    }
}
