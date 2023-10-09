package com.jin.OrderService.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="PRODUCT-SERVICE/product")
public interface ProductServiceFeignClient {
    //將 product service 裡的 api dependency 注入進來
    @PutMapping("/reduceQuantity")
    public ResponseEntity<Void> reduceProductQuantity(@RequestParam long id, @RequestParam long quantity);
}
