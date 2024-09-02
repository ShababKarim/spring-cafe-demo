package com.shabab.spring_cafe_demo.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import reactor.util.function.Tuple2;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class Order {

    @Id
    private Long id;
    private String customerName;
    private Double price;

    // Storing json for simplicity
    private String drinks;
    private LocalDateTime updatedOn;

    public Order(String customerName, List<Tuple2<MenuItem, Integer>> drinks, ObjectMapper objectMapper)
            throws JsonProcessingException {
        this.customerName = customerName;
        this.price = drinks.stream()
                .map(tuple2 -> tuple2.getT1().getPrice() * tuple2.getT2())
                .reduce(0.0, Double::sum);
        this.drinks = objectMapper.writeValueAsString(
                drinks.stream()
                        .collect(Collectors.toMap(t -> t.getT1().getName(), Tuple2::getT2))
        );
        this.updatedOn = LocalDateTime.now();
    }
}
