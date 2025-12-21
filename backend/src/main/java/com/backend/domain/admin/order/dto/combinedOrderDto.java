package com.backend.domain.admin.order.dto;

import java.util.List;

public record combinedOrderDto(
        String email,
        String address,
        String zipCode,
        List<CombinedOrderItemDetail> combinedOrderItems
){}