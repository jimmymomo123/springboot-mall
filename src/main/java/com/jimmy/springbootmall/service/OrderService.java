package com.jimmy.springbootmall.service;

import com.jimmy.springbootmall.dto.CreateOrderRequest;
import com.jimmy.springbootmall.dto.OrderQueryParam;
import com.jimmy.springbootmall.model.Order;

import java.util.List;

public interface OrderService {

    Integer countOrder(OrderQueryParam orderQueryParam);
    List<Order> getOrders(OrderQueryParam orderQueryParam);
    Order getOrderById(Integer orderId);
    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
}
