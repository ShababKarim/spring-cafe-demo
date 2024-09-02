package com.shabab.spring_cafe_demo.domain;

import java.util.Map;

public record PlaceOrderDto(String customer,
                            Map<Long, Integer> drinks,
                            Map<String, String> paymentDetails) {
}
