package com.jin.PaymentService.service;

import com.jin.PaymentService.entity.PaymentEntity;
import com.jin.PaymentService.model.PaymentRequest;
import com.jin.PaymentService.repository.PaymentRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class PaymentServiceImpl implements PaymentService{
    PaymentRepository paymentRepository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public long doPayment(PaymentRequest paymentRequest) {
        log.info("Start paymentService do payment");
        PaymentEntity paymentEntity = PaymentEntity.builder()
                .orderId(paymentRequest.getOrderId())
                .paymentMode(paymentRequest.getPaymentMode())
                .totalAmount(paymentRequest.getTotalAmount())
                .paymentDate(Instant.now())
                .paymentStatus("Success")
                .build();
        paymentRepository.save(paymentEntity);
        log.info("Start paymentService do payment with id " + paymentEntity.getPaymentId());

        return paymentEntity.getPaymentId();
    }
}
