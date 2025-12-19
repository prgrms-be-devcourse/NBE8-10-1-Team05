package com.backend.domain.order.order.service;


import com.backend.domain.order.order.dto.OrderCreateRequest;
import com.backend.domain.order.order.dto.OrderModifyRequest;
import com.backend.domain.order.order.dto.RequestedItem;
import com.backend.domain.order.order.repository.OrderRepository;
import com.backend.domain.order.orderItem.entity.OrderItem;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.backend.domain.order.order.entity.Order;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public Order create(OrderCreateRequest req) {

        Order order = Order.create(
                req.email(),
                req.zipCode(),
                req.address()
        );

        req.items().forEach(item ->
                order.addOrderItem(item.itemId(), item.quantity())
        );

        return orderRepository.save(order);
    }

    public void delete(Order order) {
        orderRepository.delete(order);
    }

    public void modify(Order order, OrderModifyRequest req) {

        order.modify(req.items());
    }

    public List<Order> findByEmail(String email) {
        return orderRepository.findByEmail(email);
    }

    public Optional<Order> findById(int order_id) {
        return orderRepository.findById(order_id);
    }

}
