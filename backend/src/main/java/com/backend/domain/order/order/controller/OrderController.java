package com.backend.domain.order.order.controller;

import com.backend.domain.order.order.service.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.backend.domain.order.order.dto.OrderDto;
import com.backend.domain.order.order.entity.Order;

import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    //주문 시 내용을 받아오는 형식
    record OrderCreateRequestBody(
            @NotBlank
            @Size(min = 2, max = 80)
            String email,
            @NotBlank
            @Size(min = 2, max = 80)
            String zipCode,
            @NotBlank
            @Size(min = 2, max = 200)
            String address
    ){}

    @PostMapping("/create")
    @Transactional
    @Operation(summary = "최초 주문")
    public RsData<OrderDto> create(
            @Valid @RequestBody OrderCreateRequestBody orderCreateRequestBody
    ){

        Order order = orderService.create(
                orderCreateRequestBody.email,
                orderCreateRequestBody.zipCode,
                orderCreateRequestBody.address
        ); // 수정 예정

        return new RsData<>(
                "201-1",
                "주문이 완료되었습니다",
                new  OrderDto(order)
        );
    }

    @GetMapping("/listByEmail/{email}")
    @Transactional(readOnly = true)
    @Operation(summary = "다건 조회")
    public List<OrderDto> listByEmail(
            @PathVariable String email
    ){

        List<Order> orders = OrderService.findAll();

        return orders
                .stream()
                .map(OrderDto::new)
                .toList();
    }

    @GetMapping("/detail/{order_id}")
    @Transactional(readOnly = true)
    @Operation(summary = "단건 조회")
    public OrderDto getOrderDetail(
            @PathVariable int order_id
    ){
        Order order = orderService.getOrderDetail(order_id).get();

        return new OrderDto(order);
    }

    @PutMapping("/modify/{order_id}")
    @Transactional
    @Operation(summary = "주문 수정")
    public RsData<Void> modifyOrder(
            @PathVariable int order_id
    ){

    }

    @DeleteMapping("/cancel/{order_id}")
    @Transactional
    @Operation(summary = "주문 취소")
    public RsData<OrderDto> cancelOrder(
            @PathVariable int order_id
    ){
        Order order = orderService.findById(order_id).get();

        orderService.delete(order);

        return new RsData<>(
                "200-1",
                "주문 취소가 완료되었습니다.",
                new OrderDto(order)
        );
    }
}
