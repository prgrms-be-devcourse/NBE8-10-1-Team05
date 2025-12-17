package com.backend.domain.order.order.service;

import com.backend.domain.order.order.controller.OrderController;
import com.backend.domain.order.order.repository.OrderRepository;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.backend.domain.order.order.entity.Order;
import com.backend.domain.item.item.entity.Item;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Order create(String email, String zipCode, String address, @NotEmpty List<Item> items) {
        Order order = new Order(email, zipCode, address, items);

        return orderRepository.save(order);
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Optional<Order> findById(int order_id) {
        return orderRepository.findById(order_id);
    }

    public void delete(Order order) {
        orderRepository.delete(order);
    }

    //TODO 작업 중지
    public void modify(Order order, List<Item> items) {
        order.modify(items);
    }
    //다건 검색
    public List<Order> findByEmail(String email) {
        return orderRepository.findByEmail(email);
    }

}
