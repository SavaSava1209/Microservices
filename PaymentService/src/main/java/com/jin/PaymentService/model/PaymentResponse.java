package com.jin.PaymentService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {
    private long id;
    private long orderId;
    private String paymentStatus;
    private String paymentMode;
    private Instant paymentDate;
    private long totalAmount;
}
