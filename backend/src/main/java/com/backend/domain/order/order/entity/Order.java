package com.backend.domain.order.order.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.FetchType.LAZY;


@Entity
@Getter
@NotBlank
public class Order{

    private int id;
    private String email;
    private String address;
    private String zipCode;

    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private LocalDateTime dueDate; // 해당 로직은 해당 클래스(엔티티)에 구현할 예정


    //수정 예정
    public Order(String email, String address, String zipCode) {
        this.email = email;
        this.address = address;
        this.zipCode = zipCode;
    }

}
