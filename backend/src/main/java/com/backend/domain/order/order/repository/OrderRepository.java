package com.backend.domain.order.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.backend.domain.order.order.entity.Order;

import java.time.LocalDateTime;
import java.util.List;


public interface OrderRepository extends JpaRepository<Order,Integer> {
    List<Order> findByEmail(String email);

    @Query("SELECT o FROM Order o WHERE DATE(o.dueDate) = DATE(:dueDate)")
    List<Order> findByDueDate(@Param("dueDate") LocalDateTime dueDate);
}
