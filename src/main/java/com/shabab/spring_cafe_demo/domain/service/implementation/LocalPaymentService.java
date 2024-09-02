package com.shabab.spring_cafe_demo.domain.service.implementation;

import com.shabab.spring_cafe_demo.domain.service.PaymentService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@Profile("local")
public class LocalPaymentService implements PaymentService {

    @Override
    public Mono<Boolean> makePayment(Map<String, String> paymentDetails) {
        return Mono.just(Boolean.TRUE);
    }
}
