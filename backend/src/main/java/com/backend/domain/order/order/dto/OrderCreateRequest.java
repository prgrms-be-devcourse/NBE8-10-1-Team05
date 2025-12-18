package com.backend.domain.order.order.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record OrderCreateRequest(

        @NotBlank
        @Size(min = 2, max = 80)
        String email,

        @NotBlank
        @Size(min = 2, max = 80)
        String zipCode,

        @NotBlank
        @Size(min = 2, max = 200)
        String address,

        @NotEmpty
        @Valid
        List<RequestedItem> items
) {}

