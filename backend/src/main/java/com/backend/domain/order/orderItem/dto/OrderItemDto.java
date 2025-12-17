package com.backend.domain.order.orderItem.dto;

import com.backend.domain.order.orderItem.entity.OrderItem;
import com.backend.domain.item.item.entity.Item;

import java.util.List;

public record OrderItemDto(
        List<Item> items
) {
    public OrderItemDto(OrderItem orderItem) {
        this(
                orderItem.getItems()
        );
    }
}
