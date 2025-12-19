package com.backend.domain.admin.order.dto;

import com.backend.domain.order.orderItem.entity.OrderItem;

import java.util.List;

public record combinedOrderDto(
        String email,
        String address,
        String zipCode,
        List<OrderItem> combinedOrderItems
){}