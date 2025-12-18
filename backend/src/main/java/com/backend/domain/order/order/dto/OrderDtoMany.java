package com.backend.domain.order.order.dto;

import java.time.LocalDateTime;

import com.backend.domain.order.order.entity.Order;

public record OrderDtoMany(
        int id,
        String email,
        String address,
        String zipCode,
        LocalDateTime createDate,
        LocalDateTime modifyDate,
        LocalDateTime dueDate
){

    public OrderDtoMany(Order order){
        this(
                order.getId(),
                order.getEmail(),
                order.getAddress(),
                order.getZipCode(),
                order.getCreateDate(),
                order.getModifyDate(),
                order.getDueDate()
        );
    }

}
