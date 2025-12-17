package com.backend.domain.order.orderItem.dto;

import com.backend.domain.order.orderItem.entity.OrderItem;

public record OrderItemDto(
        String itemName,
        int quantity
) {
    public OrderItemDto(OrderItem item) {
        this(
                item.getItemName(),
                item.getQuantity()
        );
    }
}
