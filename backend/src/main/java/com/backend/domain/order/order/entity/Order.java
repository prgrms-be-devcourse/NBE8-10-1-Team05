package com.backend.domain.order.order.entity;

import com.backend.domain.order.orderItem.entity.OrderItem;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.Id;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Getter
@NotBlank
@EntityListeners(AuditingEntityListener.class)
@Table(name = "orderDetail")
public class Order{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String email;
    private String address;
    private String zipCode;

    @OneToOne
    @JoinColumn(name = "order_detail_id")
    private List<OrderItem> orderItems;

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

    protected Order() {}

    public static Order create(String email, String address, String zipCode, List<OrderItem> items){
        Order order = new Order();
        order.email = email;
        order.address = address;
        order.zipCode = zipCode;
        order.orderItems = items;

        return order;
    }

    //상품 리스트만 받아와서 수정
    public void modify(List<OrderItem> items) {
        this.orderItems = items;
        this.modifyDate = LocalDateTime.now();
    }
}
