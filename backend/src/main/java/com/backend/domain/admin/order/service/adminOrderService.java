package com.backend.domain.admin.order.service;

import com.backend.domain.admin.order.dto.CombinedOrderItemDetail;
import com.backend.domain.admin.order.dto.combinedOrderDto;
import com.backend.domain.item.item.entity.Item;
import com.backend.domain.item.item.service.ItemService;
import com.backend.domain.order.order.entity.Order;
import com.backend.domain.order.order.repository.OrderRepository;
import com.backend.domain.order.orderItem.entity.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class adminOrderService {
    private final ItemService itemService;
    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public List<combinedOrderDto> getOrdersByDueDate(String dueDateString) {
        LocalDateTime dueDate = parseDueDate(dueDateString);
        List<Order> orders = orderRepository.findByDueDate(dueDate); //먼저 모든 해당 마감일 날짜에 해당하는 모든 Order를 가져옴
        Map<String, List<Order>> ordersByEmail = orders.stream()//그런 뒤 모든 Order를 email별로 분류, 맵에다가 넣음.
                .collect(Collectors.groupingBy(Order::getEmail));

        return ordersByEmail.entrySet().stream()
                .map(entry -> createCombinedOrderDto(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    private LocalDateTime parseDueDate(String dueDateString) {
        try {
            LocalDate date = LocalDate.parse(dueDateString);
            return LocalDateTime.of(date, LocalTime.of(14, 0));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("올바른 날짜 형식이 아닙니다.");
        }
    }

    //combinedOrderDto(통합오더 DTO)에 있는 정보를 찾아서 생성
    private combinedOrderDto createCombinedOrderDto(String email, List<Order> orders) {
        String address = orders.get(0).getAddress(); //주소
        String zipCode = orders.get(0).getZipCode(); // 우편번호

        // 각 주문의 orderItems를 하나의 리스트로 넣기
        List<OrderItem> orderItems = orders.stream()
                .flatMap(order -> order.getOrderItems().stream())
                .toList();

        // 상품 개수를 합산하기
        List<CombinedOrderItemDetail> combinedOrderItemDetails = getCombinedOrderItemDetail(orderItems);

        return new combinedOrderDto(email, address, zipCode, combinedOrderItemDetails);
    }

    private List<CombinedOrderItemDetail> getCombinedOrderItemDetail(List<OrderItem> orderItems) {
        List<CombinedOrderItemDetail> list = new ArrayList<>();

        for (OrderItem oi : orderItems) {
            Item i = itemService.findById(oi.getItemId()).orElse(null);
            String itemName = (i == null) ? "삭제된 상품" : i.getName();

            // 이미 list에 있는 상품이면 개수 합산
            Optional<CombinedOrderItemDetail> found = list.stream()
                    .filter(item -> item.getId() == oi.getItemId())
                    .findFirst();
            if (found.isPresent()) {
                found.get().addQuantity(oi.getQuantity());
            } else {
                // list에 없는 상품이면 삽입
                list.add(new CombinedOrderItemDetail(oi.getItemId(), itemName, oi.getQuantity()));
            }
        }
        return list;
    }
}
