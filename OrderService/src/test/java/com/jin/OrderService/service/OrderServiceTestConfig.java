package com.jin.OrderService.service;


import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@TestConfiguration
public class OrderServiceTestConfig  {

    @Bean
    public ServiceInstanceListSupplier supplier() {
        return new ServiceInstanceListSupplierTest();
    }



}
