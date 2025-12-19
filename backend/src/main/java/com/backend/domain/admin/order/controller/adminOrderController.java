package com.backend.domain.admin.order.controller;

import com.backend.domain.admin.order.dto.combinedOrderDto;
import com.backend.domain.admin.order.service.adminOrderService;
import com.backend.global.rsData.RsData;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/orders")
@RequiredArgsConstructor
public class adminOrderController {

    private final adminOrderService adminOrderService;

    @Transactional(readOnly = true)
    @GetMapping("/listByDueDate/{dueDate}")
    @Operation(summary = "배송 예정일별 주문 조회 (이메일별 합산)")
    public RsData<List<combinedOrderDto>> getOrdersByDueDate(@PathVariable String dueDate){
        try {
            List<combinedOrderDto> orders = adminOrderService.getOrdersByDueDate(dueDate);
            return new RsData<>(
                    "200-1",
                    "조회가 완료되었습니다.",
                    orders
            );
        } catch (IllegalArgumentException e) {
            return new RsData<>(
                    "400-1",
                    e.getMessage(),
                    null
            );
        }
    }
}
