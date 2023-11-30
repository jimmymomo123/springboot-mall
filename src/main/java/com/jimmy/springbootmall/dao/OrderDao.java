package com.jimmy.springbootmall.dao;

import com.jimmy.springbootmall.dto.CreateOrderRequest;
import com.jimmy.springbootmall.dto.OrderQueryParam;
import com.jimmy.springbootmall.model.Order;
import com.jimmy.springbootmall.model.OrderItem;

import java.util.List;

public interface OrderDao {

    Integer countOrder(OrderQueryParam orderQueryParam);
    List<Order> getOrders(OrderQueryParam orderQueryParam);
    Order getOrderById(Integer orderId);
    List<OrderItem> getOrderItemsByOrderId(Integer orderId);
    Integer createOrder(Integer userId, Integer totalAmount);

    void createOrderItems(Integer orderId, List<OrderItem> orderItemList);
}
