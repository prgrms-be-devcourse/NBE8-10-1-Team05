package com.backend.domain.order.orderItem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import com.backend.domain.order.order.entity.Order;


@Entity
@Getter
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

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
