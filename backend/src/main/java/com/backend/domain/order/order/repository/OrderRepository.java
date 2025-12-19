package com.backend.domain.order.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.backend.domain.order.order.entity.Order;

import java.util.List;


public interface OrderRepository extends JpaRepository<Order,Integer> {
    List<Order> findByEmail(String email);
}
