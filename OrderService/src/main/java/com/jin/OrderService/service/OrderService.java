package com.jin.OrderService.service;

import com.jin.OrderService.model.OrderRequest;
import com.jin.OrderService.model.OrderResponse;


public interface OrderService {

    long placeOrder(OrderRequest orderRequest); // return orderId
    OrderResponse getOrderDetailsById(long orderId);
}
