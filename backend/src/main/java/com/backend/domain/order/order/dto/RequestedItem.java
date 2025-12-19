package com.backend.domain.order.order.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record RequestedItem(
        @NotNull int itemId,
        @Min(1) int quantity
) {}

