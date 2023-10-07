package com.jin.OrderService.service;

import com.jin.OrderService.model.OrderRequest;
import com.jin.OrderService.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService{
    OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public long placeOrder(OrderRequest orderRequest) {
        // use order service to create an orderentity with status CREATED, ORM JPA save to database

        // call product service to check product quantity, if ok reduce it else throw not enough quantity exception

        // call Payment service to charge, if success, mark order PAID, else CANCELLED
        return 88;

    }
}
