package com.jin.OrderService.service;

import com.jin.OrderService.client.PaymentServiceFeignClient;
import com.jin.OrderService.client.ProductServiceFeignClient;
import com.jin.OrderService.entity.OrderEntity;
import com.jin.OrderService.model.OrderResponse;
import com.jin.OrderService.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.security.PrivateKey;
import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceImplTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductServiceFeignClient productServiceFeignClient;
    @Mock
    private PaymentServiceFeignClient paymentServiceFeignClient;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private OrderServiceImpl orderService = new OrderServiceImpl();


    @Test
    @DisplayName("GetOrderById - Success")
    void TestWhenGetOrderDetailByIdSuccess(){
        // mock call
        OrderEntity orderEntity = getMockOrderEntity();
        Mockito.when(orderRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(orderEntity));

        Mockito.when(restTemplate.getForObject(
                "http://PRODUCT-SERVICE/product/" + orderEntity.getProductId()
                    ,OrderResponse.ProductResponse.class)
        ).thenReturn(getMockProductResponse());

        Mockito.when(restTemplate.getForObject(
                "http://PAYMENT-SERVICE/payment/" + orderEntity.getId(),
                OrderResponse.PaymentResponse.class)
        ).thenReturn(getMockPaymentResponse());

        // actual call
        OrderResponse orderResponse = orderService.getOrderDetailsById(1);

        // verify call
        Mockito.verify(orderRepository, Mockito.times(1)).findById(Mockito.anyLong());

        // assert result
        Assertions.assertNotNull(orderResponse);
        Assertions.assertEquals(orderEntity.getId(), orderResponse.getOrderId()); // expect, actual

    }



    private OrderEntity getMockOrderEntity(){
        return OrderEntity.builder()
                .id(7)
                .productId(2)
                .quantity(2)
                .totalAmount(2999)
                .orderDate(Instant.now())
                .orderStatus("PLACED")
                .build();
    }
    private OrderResponse.ProductResponse getMockProductResponse(){
        return OrderResponse.ProductResponse.builder()
                .id(2)
                .name("MacMini")
                .quantity(2)
                .price(1499)
                .build();
    }
    private OrderResponse.PaymentResponse getMockPaymentResponse(){
        return OrderResponse.PaymentResponse.builder()
                .id(1)
                .orderId(7)
                .paymentMode("VISA")
                .paymentDate(Instant.now())
                .paymentStatus("SUCCESS")
                .totalAmount(2999)
                .build();
    }



}