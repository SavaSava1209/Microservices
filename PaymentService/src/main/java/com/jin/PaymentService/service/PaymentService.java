package com.jin.PaymentService.service;

import com.jin.PaymentService.model.PaymentRequest;
import com.jin.PaymentService.model.PaymentResponse;

public interface PaymentService {
    long doPayment(PaymentRequest paymentRequest);
    PaymentResponse getPaymentDetailsByOrderId(long orderId);
}
