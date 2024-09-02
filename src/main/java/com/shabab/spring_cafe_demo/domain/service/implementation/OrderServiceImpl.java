package com.shabab.spring_cafe_demo.domain.service.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shabab.spring_cafe_demo.domain.MenuItem;
import com.shabab.spring_cafe_demo.domain.Order;
import com.shabab.spring_cafe_demo.domain.PlaceOrderDto;
import com.shabab.spring_cafe_demo.domain.repository.MenuItemRepository;
import com.shabab.spring_cafe_demo.domain.repository.OrderRepository;
import com.shabab.spring_cafe_demo.domain.service.OrderService;
import com.shabab.spring_cafe_demo.domain.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final PaymentService paymentService;
    private final OrderRepository orderRepository;
    private final MenuItemRepository menuItemRepository;
    private final ObjectMapper objectMapper;

    public OrderServiceImpl(
                            PaymentService paymentService,
                            OrderRepository orderRepository,
                            MenuItemRepository menuItemRepository,
                            ObjectMapper objectMapper) {
        this.paymentService = paymentService;
        this.orderRepository = orderRepository;
        this.menuItemRepository = menuItemRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public Flux<Order> getAllOrders() {
        return Mono.fromRunnable(() -> log.info("Retrieving all orders"))
                .then(Mono.fromCallable(() -> orderRepository.findAll()))
                .flatMapIterable(Function.identity());
    }

    @Transactional
    @Override
    public Mono<Order> createOrder(PlaceOrderDto dto) {
        return Mono.fromRunnable(() -> log.info("Processing order for customer {}", dto.customer()))
                .then(processPayment(dto))
                .flatMapMany(paymentSucceeded -> Flux.fromIterable(dto.drinks().entrySet()))
                .flatMap(entry -> updateMenuItemQuantity(entry.getKey(), entry.getValue()))
                .collectList()
                .<Order>handle((drinks, sink) -> {
                    try {
                        sink.next(new Order(dto.customer(), drinks, objectMapper));
                    } catch (JsonProcessingException e) {
                        sink.error(new RuntimeException(e));
                    }
                })
                .flatMap(order -> Mono.fromCallable(() -> orderRepository.save(order)))
                .doOnNext(order -> log.info("Fulfilled order {} for customer {}", order.getId(), order.getCustomerName()))
                .doOnError(throwable -> log.error("Error fulfilling order with cause: {}", throwable.getMessage()));
    }

    private Mono<Boolean> processPayment(PlaceOrderDto dto) {
        return paymentService.makePayment(dto.paymentDetails())
                .doFirst(() -> log.info("Making payment for {} with details {}", dto.customer(), dto.paymentDetails()))
                .filter(v -> v)
                .switchIfEmpty(Mono.error(new RuntimeException(String.format("Payment failed for customer %s", dto.customer()))));
    }

    // Should be strictly checking if a selected menu item exists
    // Should check for quantity > available
    private Mono<Tuple2<MenuItem, Integer>> updateMenuItemQuantity(Long menuItemId, Integer quantity) {
        return Mono.fromCallable(() -> menuItemRepository.findById(menuItemId))
                .flatMap(Mono::justOrEmpty)
                .doOnNext(menuItem -> log.info("Updating menu item {} with new count {}", menuItem.getName(), menuItem.getQuantity() - quantity))
                .flatMap(menuItem -> Mono.fromCallable(() -> menuItemRepository.updateMenuItemQuantity(
                                menuItemId,
                                menuItem.getQuantity() - quantity))
                        .map(rowCount -> Tuples.of(menuItem, quantity))
                )
                .onErrorResume(throwable -> {
                    log.error("Error updating menu item {} with new count from cause: {}", menuItemId, throwable.getMessage());
                    return Mono.empty();
                });
    }
}
