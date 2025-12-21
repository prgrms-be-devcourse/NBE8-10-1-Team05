package com.backend.domain.order.orderItem.dto;

import com.backend.domain.item.item.entity.Item;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
public record OrderItemDetailDto(
        @NotBlank
        int itemId,
        @NotBlank
        String name,
        String category,
        @Min(1) int quantity,
        int price,
        String imageUrl
) {
    public OrderItemDetailDto(Item item, int quantity) {
        this(item.getId(),
                item.getName(),
                item.getCategory(),
                quantity,
                item.getPrice(),
                item.getImageUrl()
                );
    }
}
