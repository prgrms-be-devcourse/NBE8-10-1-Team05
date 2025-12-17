package com.backend.domain.order.orderItem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.backend.domain.order.order.entity.Order;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String itemName;
    private int quantity;

    @OneToMany(mappedBy = "orderItem")
    private List<Item> Items;
}
