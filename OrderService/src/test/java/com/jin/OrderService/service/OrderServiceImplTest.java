package com.jin.OrderService.service;

import com.jin.OrderService.client.PaymentServiceFeignClient;
import com.jin.OrderService.client.ProductServiceFeignClient;
import com.jin.OrderService.entity.OrderEntity;
import com.jin.OrderService.model.OrderRequest;
import com.jin.OrderService.model.OrderResponse;
import com.jin.OrderService.model.PaymentRequest;
import com.jin.OrderService.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Test
    @DisplayName("GetOrderById - Failed")
    void TestWhenGetOrderDetailByIdFailed(){
        // mock response
        Mockito.when(orderRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.ofNullable(null));

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
                () -> orderService.getOrderDetailsById(1));

        // mock call
        // verify call
        // assert result
        Assertions.assertEquals("order is not found with order id 1", exception.getMessage());

    }




    @Test
    @DisplayName("Place Order - Success")
    void TestWhenPlaceOrderSuccess(){
        // mock response
        OrderEntity orderEntity = getMockOrderEntity();
        OrderRequest orderRequest = getMockOrderRequest();
        Mockito.when(orderRepository.save(Mockito.any(OrderEntity.class)))
                .thenReturn(orderEntity);
        Mockito.when(productServiceFeignClient.reduceProductQuantity(Mockito.anyLong(),Mockito.anyLong()))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));
        Mockito.when(paymentServiceFeignClient.doPayment(Mockito.any(PaymentRequest.class)))
                .thenReturn(new ResponseEntity<Long>(1L, HttpStatus.OK));
        // actual call
        long orderId = orderService.placeOrder(orderRequest);
        // verify call
        Mockito.verify(orderRepository, Mockito.times(2)).save(Mockito.any());
        Mockito.verify(productServiceFeignClient, Mockito.times(1)).reduceProductQuantity(Mockito.anyLong(), Mockito.anyLong());
        Mockito.verify(paymentServiceFeignClient, Mockito.times(1)).doPayment(Mockito.any(PaymentRequest.class));

        Assertions.assertEquals(orderEntity.getId(), orderId);
    }

    @Test
    @DisplayName("PlaceOrder Failed")
    void testWhenPlaceOrderPaymentFailed() {
        OrderEntity orderEntity = getMockOrderEntity();
        OrderRequest orderRequest = getMockOrderRequest();

        Mockito.when(orderRepository.save(Mockito.any(OrderEntity.class)))
                .thenReturn(orderEntity);
        Mockito.when(productServiceFeignClient.reduceProductQuantity(Mockito.anyLong(),Mockito.anyLong()))
                .thenReturn(new ResponseEntity<Void>(HttpStatus.OK));
        Mockito.when(paymentServiceFeignClient.doPayment(Mockito.any(PaymentRequest.class)))
                .thenThrow(new RuntimeException("Payment Failed"));

        long orderId = orderService.placeOrder(orderRequest);

        Mockito.verify(orderRepository, Mockito.times(2)).save(Mockito.any());
        Mockito.verify(productServiceFeignClient, Mockito.times(1)).reduceProductQuantity(Mockito.anyLong(), Mockito.anyLong());
        Mockito.verify(paymentServiceFeignClient, Mockito.times(1)).doPayment(Mockito.any(PaymentRequest.class));

        Assertions.assertEquals(orderEntity.getId(), orderId);
    }

    private OrderRequest getMockOrderRequest() {
        return OrderRequest.builder()
                .productId(1)
                .quantity(2)
                .totalAmount(1999)
                .paymentMode("CASH")
                .build();

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