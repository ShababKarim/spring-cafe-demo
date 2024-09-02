package com.shabab.spring_cafe_demo.domain.repository;

import com.shabab.spring_cafe_demo.domain.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
