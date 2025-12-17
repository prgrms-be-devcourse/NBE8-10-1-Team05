package com.backend.domain.order.orderItem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.backend.domain.order.order.entity.Order;
import com.backend.domain.item.item.entity.Item;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private List<Item> items;

    public OrderItem(List<Item> items) {
        this.items = items;
    }

    public void modify(List<Item> items){
        this.items = items;
    }
}
