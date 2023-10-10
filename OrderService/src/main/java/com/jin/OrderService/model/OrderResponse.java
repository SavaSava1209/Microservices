package com.jin.OrderService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private long orderId;
    private Instant orderDate;
    private long totalAmount;
    private String orderStatus;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ProductResponse {
        private long id;
        private String name;
        private long price;
        private long quantity;
    }
}
