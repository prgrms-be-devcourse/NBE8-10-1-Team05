package com.backend.domain.item.item.dto;

import com.backend.domain.item.item.entity.Item;

public record ItemResponse(
        Integer id,
        String name,
        String category,
        Integer price,
        String imageUrl
) {
    public ItemResponse (Item item) {
        this(
                item.getId(),
                item.getName(),
                item.getCategory(),
                item.getPrice(),
                item.getImageUrl()
        );
    }
}