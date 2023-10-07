package com.jin.OrderService.service;

import com.jin.OrderService.model.OrderRequest;


public interface OrderService {

    long placeOrder(OrderRequest orderRequest); // return orderId
}
