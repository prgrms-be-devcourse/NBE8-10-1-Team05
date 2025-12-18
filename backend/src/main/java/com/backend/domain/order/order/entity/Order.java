package com.backend.domain.order.order.entity;

import com.backend.domain.order.order.dto.RequestedItem;
import com.backend.domain.order.orderItem.entity.OrderItem;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Id;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "orderDetail")
public class Order{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String email;
    private String address;
    private String zipCode;

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.PERSIST,
            orphanRemoval = true
    )
    private List<OrderItem> orderItems = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime modifyDate;

    private LocalDateTime dueDate; // 해당 로직은 해당 클래스(엔티티)에 구현할 예정


    //주문 기한 시각을 createDate를 기준 계산하는 로직
    @PrePersist
    private void calculateDueDate() {
        LocalDateTime base = this.createDate != null
                ? this.createDate
                : LocalDateTime.now();

        LocalDate today = base.toLocalDate();
        LocalTime deadline = LocalTime.of(14, 0);

        if (base.toLocalTime().isBefore(deadline)) {
            this.dueDate = LocalDateTime.of(today, deadline);
        } else {
            this.dueDate = LocalDateTime.of(today.plusDays(1), deadline);
        }
    }

    public OrderItem addOrderItem(int itemId, int quantity) {
        OrderItem orderItem = OrderItem.create(itemId, quantity);
        orderItem.setOrder(this);
        orderItems.add(orderItem);
        return orderItem;
    }

    protected Order() {}

    public static Order create(
            String email,
            String zipCode,
            String address,
            List<RequestedItem> items
    ) {
        Order order = new Order();
        order.email = email;
        order.zipCode = zipCode;
        order.address = address;
        order.createDate = LocalDateTime.now();

        items.forEach(item ->
                order.addOrderItem(item.itemId(), item.quantity())
        );

        return order;
    }

    //상품 리스트만 받아와서 수정
    public void modify(
            Order order,
            List<RequestedItem> items
    ) {
        this.modifyDate = LocalDateTime.now();

        orderItems.clear();

        items.forEach(item ->
                order.addOrderItem(item.itemId(), item.quantity())
        );
    }
}
