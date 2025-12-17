package com.backend.domain.item.item.dto;

import com.backend.domain.item.item.entity.Item;

public record ItemResponse(
        Integer id,
        String name,
        String category,
        Integer price,
        String imageUrl
) {
    public static ItemResponse from(Item item) {
        return new ItemResponse(
                item.getId(),
                item.getName(),
                item.getCategory(),
                item.getPrice(),
                item.getImageUrl()
        );
    }
}