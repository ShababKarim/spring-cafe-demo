package com.shabab.spring_cafe_demo.domain.controller;

import com.shabab.spring_cafe_demo.domain.Order;
import com.shabab.spring_cafe_demo.domain.PlaceOrderDto;
import com.shabab.spring_cafe_demo.domain.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/")
    public Flux<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PostMapping("/create")
    public Mono<Order> createOrder(@RequestBody PlaceOrderDto dto) {
        return orderService.createOrder(dto);
    }
}
