package com.jin.PaymentService.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name="Payment_TB")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data // getter setter
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long paymentId;

    private long orderId;
    private String paymentMode;
    private long totalAmount;
    private Instant paymentDate;
    private String paymentStatus;


}
