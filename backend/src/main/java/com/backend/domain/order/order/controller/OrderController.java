package com.backend.domain.order.order.controller;

import com.backend.domain.order.order.dto.OrderCreateRequest;
import com.backend.domain.order.order.dto.OrderModifyRequest;
import com.backend.domain.order.order.dto.RequestedItem;
import com.backend.domain.order.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.backend.domain.order.order.dto.OrderDto;
import com.backend.domain.order.order.entity.Order;
import com.backend.global.rsData.RsData;

import io.swagger.v3.oas.annotations.Operation;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    @Transactional
    @Operation(summary = "최초 주문")
    public RsData<OrderDto> create(
            @Valid @RequestBody OrderCreateRequest req
    ){
        Order order = orderService.create(req);

        return new RsData<>(
                "201-1",
                "주문이 완료되었습니다",
                new OrderDto(order)
        );
    }

    @PutMapping("/modify/{order_id}")
    @Transactional
    @Operation(summary = "주문 수정")
    public RsData<Void> modifyOrder(
            @PathVariable int order_id,
            @Valid @RequestBody OrderModifyRequest req
    ){
        Order order = orderService.findById(order_id).get();

        orderService.modify(
                order,
                req
        );

        return new RsData<>(
                "200-1",
                "주문 수정이 완료되었습니다."
        );
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


    @GetMapping("/listByEmail/{email}")
    @Transactional(readOnly = true)
    @Operation(summary = "다건 조회")
    public List<OrderDto> findByEmail(
            @PathVariable String email
    ){

        List<Order> orders = orderService.findByEmail(email);

        return orders
                .stream()
                .map(OrderDto::new)
                .toList();
    }


    @GetMapping("/detail/{order_id}")
    @Transactional(readOnly = true)
    @Operation(summary = "단건 조회")
    public List<RequestedItem> getOrderDetail(
            @PathVariable int order_id
    ){
        Order order = orderService.findById(order_id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // OrderItem 리스트를 RequestedItem 리스트로 변환
        return order.getOrderItems().stream()
                .map(oi -> new RequestedItem(oi.getItemId(), oi.getQuantity()))
                .collect(Collectors.toList());
    }

}
