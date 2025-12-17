package com.backend.domain.order.order.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.backend.domain.order.order.entity.Order;
import com.backend.domain.order.orderItem.dto.OrderItemDto;

public record OrderDto (
        int id,
        String email,
        String address,
        String zipCode,
        LocalDateTime createDate,
        LocalDateTime modifyDate,
        LocalDateTime dueDate,
        List<OrderItemDto> items
){

    public OrderDto(Order order){
        this(
                order.getId(),
                order.getEmail(),
                order.getAddress(),
                order.getZipCode(),
                order.getCreateDate(),
                order.getModifyDate(),
                order.getDueDate(),
                order.getItems().stream()
                        .map(OrderItemDto::new)
                        .toList()
        );
    }

}
