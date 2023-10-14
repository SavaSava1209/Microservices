package com.jin.OrderService.service;

import com.jin.OrderService.client.PaymentServiceFeignClient;
import com.jin.OrderService.client.ProductServiceFeignClient;
import com.jin.OrderService.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.security.PrivateKey;

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

        // actual call


        // verify call

        // assert result
    }
}