package com.backend.domain.order.orderItem.entity;

import com.backend.global.jpa.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import com.backend.domain.order.order.entity.Order;


@Entity
@Getter
public class OrderItem extends BaseEntity {

    @ManyToOne
    @JoinColumn
    private Order order;
    private int itemId;
    private int quantity;

    protected OrderItem() {}

    public static OrderItem create(int itemId, int quantity) {

        OrderItem orderItem = new OrderItem();
        orderItem.itemId = itemId;
        orderItem.quantity = quantity;

        return orderItem;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
