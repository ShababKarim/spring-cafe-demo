package com.shabab.spring_cafe_demo.domain.service;

import com.shabab.spring_cafe_demo.domain.Order;
import com.shabab.spring_cafe_demo.domain.PlaceOrderDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderService {

    Flux<Order> getAllOrders();

    Mono<Order> createOrder(PlaceOrderDto dto);
}
