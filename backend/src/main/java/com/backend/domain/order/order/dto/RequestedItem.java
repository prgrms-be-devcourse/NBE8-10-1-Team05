package com.backend.domain.order.order.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RequestedItem(
        @NotNull(message = "상품 ID는 필수입니다.")
        int itemId,
        @NotNull(message = "수량은 필수입니다.")
        @Min(1) int quantity
) {}

