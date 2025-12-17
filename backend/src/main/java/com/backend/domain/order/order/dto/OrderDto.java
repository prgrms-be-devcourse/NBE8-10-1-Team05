package com.backend.domain.order.order.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.backend.domain.order.order.entity.Order;
import com.backend.domain.order.orderItem.dto.OrderItemDto;
import com.backend.domain.order.orderItem.entity.OrderItem;

public record OrderDto (
        int id,
        String email,
        String address,
        String zipCode,
        LocalDateTime createDate,
        LocalDateTime modifyDate,
        LocalDateTime dueDate,
        OrderItem orderItem
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
                order.getOrderItem()
        );
    }

}
