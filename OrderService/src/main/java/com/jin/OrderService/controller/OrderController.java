package com.jin.OrderService.controller;

import com.jin.OrderService.model.OrderRequest;
import com.jin.OrderService.model.OrderResponse;
import com.jin.OrderService.service.OrderService;
import com.netflix.discovery.converters.Auto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.PathParam;

@RestController
@RequestMapping("/order")
@Log4j2
public class OrderController {
    OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/placeOrder") // integrate with product service
    public ResponseEntity<Long> placeOrder(@RequestBody OrderRequest orderRequest){
        log.info("Start: OrderController placeOrder");
        long orderId = orderService.placeOrder(orderRequest);
        log.info("End: OrderController  placeOrder");
        return new ResponseEntity<>(orderId, HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderDetailsById(@PathVariable long orderId){
        OrderResponse orderResponse = orderService.getOrderDetailsById(orderId);

        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }



}
