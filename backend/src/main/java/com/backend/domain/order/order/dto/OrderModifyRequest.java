package com.backend.domain.order.order.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record OrderModifyRequest(
        @NotEmpty
        @Valid
        List<RequestedItem> items
) {}