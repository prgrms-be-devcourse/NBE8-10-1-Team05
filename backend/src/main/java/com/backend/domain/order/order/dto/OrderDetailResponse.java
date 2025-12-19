package com.backend.domain.order.order.dto;

import com.backend.domain.order.order.entity.Order;
import com.backend.domain.order.orderItem.dto.OrderItemDetailDto;
import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;

public record OrderDetailResponse(
        int id,
        String email,
        String address,
        String zipCode,
        LocalDateTime createDate,
        LocalDateTime modifyDate,
        @Valid
        List<OrderItemDetailDto> orderItems,
        int total
)
{
    public OrderDetailResponse(Order order, List<OrderItemDetailDto> items, int total) {
        this(order.getId(), order.getEmail(), order.getAddress(), order.getZipCode(), order.getCreateDate(),
                order.getModifyDate(), items, total);
    }
}