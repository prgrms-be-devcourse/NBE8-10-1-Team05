package com.backend.domain.order.order.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record RequestedItem(
        @NotBlank int itemId,
        @Min(1) int quantity
) {}

