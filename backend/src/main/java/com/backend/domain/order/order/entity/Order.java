package com.backend.domain.order.order.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String email;
    private String address;
    private String zipCode;


    //basEntity로 공통화되면 제거 예정
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private LocalDateTime dueDate; // 해당 로직은 해당 클래스(엔티티)에 구현할 예정


    //수정 예정
    public Order(String email, String address, String zipCode) {
        this.email = email;
        this.address = address;
        this.zipCode = zipCode;
        this.createDate = LocalDateTime.now();
    }

}
