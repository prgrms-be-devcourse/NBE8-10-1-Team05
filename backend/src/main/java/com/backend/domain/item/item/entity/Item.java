package com.backend.domain.item.item.entity;


import com.backend.domain.order.orderItem.entity.OrderItem;
import com.backend.global.jpa.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Item extends BaseEntity{
    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false, length = 20)
    private String category;

    @Column(length = 100)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    private OrderItem orderItem;

    public Item(String name, String category, int price, String imageUrl) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public void modify(String name, String category, int price, String imageUrl) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.imageUrl = imageUrl;
    }
}
