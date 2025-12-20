package com.backend.domain.admin.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CombinedOrderItemDetail {
    private int id;
    private String name;
    private int quantity;

    public void addQuantity(int amount) {
        this.quantity += amount;
    }
}
