package com.jin.OrderService.service;

import com.jin.OrderService.client.PaymentServiceFeignClient;
import com.jin.OrderService.client.ProductServiceFeignClient;
import com.jin.OrderService.entity.OrderEntity;
import com.jin.OrderService.model.OrderRequest;
import com.jin.OrderService.model.OrderResponse;
import com.jin.OrderService.model.PaymentRequest;
import com.jin.OrderService.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.persistence.criteria.Order;
import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService{
    OrderRepository orderRepository;
    ProductServiceFeignClient productServiceFeignClient;
    PaymentServiceFeignClient paymentServiceFeignClient;
    RestTemplate restTemplate ;


    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            ProductServiceFeignClient productServiceFeignClient,
                            PaymentServiceFeignClient paymentServiceFeignClient,
                            RestTemplate restTemplate
    ) {
        this.orderRepository = orderRepository;
        this.productServiceFeignClient = productServiceFeignClient;
        this.paymentServiceFeignClient = paymentServiceFeignClient;
        this.restTemplate = restTemplate;
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


        PaymentRequest paymentRequest = PaymentRequest.builder()
                .orderId(orderEntity.getId())
                .paymentMode(orderRequest.getPaymentMode())
                .totalAmount(orderRequest.getTotalAmount())
                .build();

        String orderStatus = null;
        try {
            paymentServiceFeignClient.doPayment(paymentRequest);
            log.info("Process Service placeOrder feignCall paymentService doPayment Start");

            orderStatus = "PAID";
            log.info("Process Service placeOrder feignCall paymentService doPayment PAID");
        } catch (Exception e){
            orderStatus = "PAYMENT_FAILED";
            log.info("Process: Service placeOrder feignCall paymentService doPayment FAILED");
        }
        orderEntity.setOrderStatus(orderStatus);
        orderRepository.save(orderEntity);

        log.info("End: OrderService placeOrder");

        return orderEntity.getId();

    }

    @Override
    public OrderResponse getOrderDetailsById(long orderId) {
        log.info("Start: OrderService getOrderDetailsById");
        OrderEntity orderEntity = orderRepository.findById(orderId)
                        .orElseThrow(() -> new RuntimeException("order is not found with order id " + orderId));

        OrderResponse.ProductResponse productResponse = restTemplate.getForObject(
                "http://localhost:8001/product/" + orderEntity.getProductId(),
                OrderResponse.ProductResponse.class
        );

        OrderResponse.PaymentResponse paymentResponse = restTemplate.getForObject(
                "http://localhost:8003/payment/" + orderEntity.getId(),
                OrderResponse.PaymentResponse.class
        );

        OrderResponse orderResponse = OrderResponse.builder()
                .orderId(orderEntity.getId())
                .orderStatus(orderEntity.getOrderStatus())
                .orderDate(orderEntity.getOrderDate())
                .totalAmount(orderEntity.getTotalAmount())
                .productResponse(productResponse)
                .paymentResponse(paymentResponse)
                .build();
        log.info("End: OrderService getOrderDetailsById");

        return orderResponse;
    }
}
