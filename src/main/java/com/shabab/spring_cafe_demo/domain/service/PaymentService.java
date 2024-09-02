package com.shabab.spring_cafe_demo.domain.service;

import reactor.core.publisher.Mono;

import java.util.Map;

public interface PaymentService {
    Mono<Boolean> makePayment(Map<String, String> paymentDetails);
}
