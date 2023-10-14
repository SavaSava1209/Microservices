package com.jin.OrderService.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="PRODUCT-SERVICE/product")
@CircuitBreaker(name = "feignCall", fallbackMethod = "feignCallProductFallback")
public interface ProductServiceFeignClient {
    //將 product service 裡的 api dependency 注入進來
    @PutMapping("/reduceQuantity")
    public ResponseEntity<Void> reduceProductQuantity(@RequestParam long id, @RequestParam long quantity);

    default void feignCallProductFallback(Exception e){
        throw new RuntimeException("Product Service is down");
    }
}
