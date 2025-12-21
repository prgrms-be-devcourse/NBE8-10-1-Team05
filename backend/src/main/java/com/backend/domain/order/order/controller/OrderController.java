package com.backend.domain.order.order.controller;

import com.backend.domain.item.item.entity.Item;
import com.backend.domain.item.item.service.ItemService;
import com.backend.domain.order.order.dto.OrderCreateRequest;
import com.backend.domain.order.order.dto.OrderDetailResponse;
import com.backend.domain.order.order.dto.OrderDto;
import com.backend.domain.order.order.dto.OrderModifyRequest;
import com.backend.domain.order.order.entity.Order;
import com.backend.domain.order.order.service.OrderService;
import com.backend.domain.order.orderItem.dto.OrderItemDetailDto;
import com.backend.global.rsData.RsData;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final ItemService itemService;
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

        if (LocalDateTime.now().isAfter(order.getDueDate())) {
            return new RsData<>(
                    "400-1",
                    "수정 불가한 주문입니다."
            );
        }

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

        if (LocalDateTime.now().isAfter(order.getDueDate())) {
            return new RsData<>(
                    "400-1",
                    "취소 불가한 주문입니다."
            );
        }

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

    //주문서 수정 페이지/주문 단건 조회 페이지에 쓰임
    @GetMapping("/detail/{order_id}")
    @Transactional(readOnly = true)
    @Operation(summary = "단건 조회")
    public OrderDetailResponse getOrderDetail(@PathVariable int order_id){

        Order order = orderService.findById(order_id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        //lambda 안에서 총 가격 계산을 작업하기 위해 int total 대신 AtomicInteger total를 사용
        AtomicInteger total = new AtomicInteger();
        // OrderItem 리스트를 OrderItemDetailDto 리스트로 변환
        List<OrderItemDetailDto> items = order.getOrderItems().stream().map(
                oi-> {
                    Item i = itemService.findById(oi.getItemId())
                            .orElseThrow(() -> new RuntimeException("Item #" + oi.getItemId() + " exists in Order #" + oi.getId() + " but does not found in Item DB"));
                    total.addAndGet(i.getPrice() * oi.getQuantity());
                    return new OrderItemDetailDto(i, oi.getQuantity());
                }).collect(Collectors.toList());
        return new OrderDetailResponse(order, items, total.get());
    }

}
