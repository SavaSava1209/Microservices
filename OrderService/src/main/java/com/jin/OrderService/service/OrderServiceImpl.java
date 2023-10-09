package com.jin.OrderService.service;

import com.jin.OrderService.client.ProductServiceFeignClient;
import com.jin.OrderService.entity.OrderEntity;
import com.jin.OrderService.model.OrderRequest;
import com.jin.OrderService.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService{
    OrderRepository orderRepository;
    ProductServiceFeignClient productServiceFeignClient;
    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,ProductServiceFeignClient productServiceFeignClient) {
        this.orderRepository = orderRepository;
        this.productServiceFeignClient = productServiceFeignClient;
    }



    @Override
    public long placeOrder(OrderRequest orderRequest) {
        log.info("Start: OrderService placeOrder");

        // use order service to create an orderentity with status CREATED, ORM JPA save to database
        OrderEntity orderEntity = OrderEntity.builder()
                .productId(orderRequest.getProductId())
                .quantity(orderRequest.getQuantity())
                .totalAmount(orderRequest.getTotalAmount())
                .orderDate(Instant.now())
                .orderStatus("Created")
                .build();
        orderRepository.save(orderEntity);
        log.info("Process: Order Service place order save orderEntity with orderId " + orderEntity);
        // call product service to check product quantity, if ok reduce it else throw not enough quantity exception
        productServiceFeignClient.reduceProductQuantity(orderEntity.getProductId(), orderEntity.getQuantity());
        log.info("Process: OrderService call productService feignClient reduce quantity");
        // call Payment service to charge, if success, mark order PAID, else CANCELLED
        log.info("End: OrderService placeOrder");
        return orderEntity.getId();

    }
}
