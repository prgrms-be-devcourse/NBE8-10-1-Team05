package com.backend.domain.order.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.backend.domain.order.order.entity.Order;


public interface OrderRepository extends JpaRepository<Order,Integer> {

}
