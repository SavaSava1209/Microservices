package com.jin.OrderService.service;

import com.jin.OrderService.model.OrderRequest;
import com.jin.OrderService.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService{
    OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public long placeOrder(OrderRequest orderRequest) {
        log.info("Start: OrderService placeOrder");

        // use order service to create an orderentity with status CREATED, ORM JPA save to database

        // call product service to check product quantity, if ok reduce it else throw not enough quantity exception

        // call Payment service to charge, if success, mark order PAID, else CANCELLED
        log.info("End: OrderService placeOrder");
        return 88;

    }
}
