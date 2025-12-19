package com.backend.domain.admin.order.service;

import com.backend.domain.admin.order.dto.combinedOrderDto;
import com.backend.domain.order.order.entity.Order;
import com.backend.domain.order.order.repository.OrderRepository;
import com.backend.domain.order.orderItem.entity.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class adminOrderService {
    private final OrderRepository orderRepository;
    @Transactional(readOnly = true)
    public List<combinedOrderDto> getOrdersByDueDate(String dueDateString) {
        LocalDateTime dueDate = parseDueDate(dueDateString);
        List<Order> orders = orderRepository.findByDueDate(dueDate); //먼저 모든 해당 마감일 날짜에 해당하는 모든 Order를 가져옴
        Map<String, List<Order>> ordersByEmail = orders.stream()//그런 뒤 모든 Order를 email별로 분류, 맵에다가 넣음.
                .collect(Collectors.groupingBy(Order::getEmail));

        return ordersByEmail.entrySet().stream()
                .map(entry -> createCombinedOrderDto(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    private LocalDateTime parseDueDate(String dueDateString) {
        try {
            LocalDate date = LocalDate.parse(dueDateString);
            return LocalDateTime.of(date, LocalTime.of(14, 0));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("올바른 날짜 형식이 아닙니다.");
        }
    }

    //combinedOrderDto(통합오더 DTO)에 있는 정보를 찾아서 생성
    private combinedOrderDto createCombinedOrderDto(String email, List<Order> orders) {
        String address = orders.get(0).getAddress(); //주소
        String zipCode = orders.get(0).getZipCode(); // 우편번호

        List<OrderItem> combinedOrderItems = orders.stream()
                .flatMap(order -> order.getOrderItems().stream())
                .collect(Collectors.toList()); // OrderItem정보 찾기

        return new combinedOrderDto(email, address, zipCode, combinedOrderItems);
    }
}