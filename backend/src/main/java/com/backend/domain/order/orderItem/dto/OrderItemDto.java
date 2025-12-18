package com.backend.domain.order.orderItem.dto;

import com.backend.domain.order.order.dto.RequestedItem;
import com.backend.domain.order.orderItem.entity.OrderItem;
import com.backend.domain.item.item.entity.Item;

import java.util.List;

public record OrderItemDto(
        int id,
        int itemId,
        int quantity
) {
    public OrderItemDto(OrderItem orderItem) {
        this(
                orderItem.getId(),
                orderItem.getItemId(),
                orderItem.getQuantity()
        );
    }
}
