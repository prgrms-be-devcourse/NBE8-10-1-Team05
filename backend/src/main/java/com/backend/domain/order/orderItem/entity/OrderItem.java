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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    private int itemId;
    private int quantity;

    public void setOrder(Order order) {
        this.order = order;
    }

    protected OrderItem() {}

    public static OrderItem create(int itemId, int quantity) {
        OrderItem orderItem = new OrderItem();
        orderItem.itemId= itemId;
        orderItem.quantity = quantity;
        return orderItem;
    }


    //TODO 수량이 0개일 때에는 어떻게 할 것인가?
    public void modify( int quantity) {
        this.quantity = quantity;
    }
}
