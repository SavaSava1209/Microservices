package com.jin.OrderService.client;

import com.jin.OrderService.model.PaymentRequest;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "PAYMENT-SERVICE/payment")
@CircuitBreaker(name = "feignCall", fallbackMethod = "feignCallPaymentFallback")
public interface PaymentServiceFeignClient {

    @PostMapping("/doPayment")
    public ResponseEntity<Long> doPayment(@RequestBody PaymentRequest paymentRequest);

    default void feignCallPaymentFallback(Exception e){
        throw new RuntimeException("Payment service is down");
    }
}
