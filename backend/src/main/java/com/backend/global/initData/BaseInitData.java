package com.backend.global.initData;

import com.backend.domain.item.item.service.ItemService;
import com.backend.domain.order.order.dto.OrderCreateRequest;
import com.backend.domain.order.order.dto.OrderModifyRequest;
import com.backend.domain.order.order.dto.RequestedItem;
import com.backend.domain.order.order.entity.Order;
import com.backend.domain.order.order.repository.OrderRepository;
import com.backend.domain.order.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class BaseInitData {
    @Autowired
    @Lazy
    private BaseInitData self;
    private final ItemService itemService;
    private final OrderService orderService;
    private final OrderRepository orderRepository;

    @Bean
    ApplicationRunner baseInitDataApplicationRunner() {
        return args -> {
            self.work1(); // item 생성
            self.work2(); // order 생성
            self.work4(); // order:1 수정
            //self.work3(); // order:1 삭제
        };
    }

    @Transactional
    public void work1() {
        itemService.createItem("Columbia Nariño", "커피콩", 5000, "https://raw.githubusercontent.com/prgrms-be-devcourse/NBE8-10-1-Team05/refs/heads/main/image/Columbia_Nari%C3%B1o.jpg");
        itemService.createItem("Brazil Serra Do Caparaó", "커피콩", 6000, "https://raw.githubusercontent.com/prgrms-be-devcourse/NBE8-10-1-Team05/refs/heads/main/image/Brazil_Serra_Do_Capara%C3%B3.png");
        itemService.createItem("Columbia Quindío (White Wine Extended Fermentation)", "커피콩", 7000, "https://raw.githubusercontent.com/prgrms-be-devcourse/NBE8-10-1-Team05/refs/heads/main/image/Columbia_Quind%C3%ADo.jpg");
        itemService.createItem("Ethiopia Sidamo", "커피콩", 8000, "https://raw.githubusercontent.com/prgrms-be-devcourse/NBE8-10-1-Team05/refs/heads/main/image/Ethiopia_Sidamo.png");
    }

    @Transactional
    public void work2() {
        OrderCreateRequest orderReq = new OrderCreateRequest(
                "john@gmail.com",
                "11111",
                "부산",
                List.of(
                        new RequestedItem(11, 22),
                        new RequestedItem(22, 11),
                        new RequestedItem(44, 33)
                )
        );
        OrderCreateRequest orderReq2 = new OrderCreateRequest(
                "tom@gmail.com",
                "22222",
                "부산",
                List.of(
                        new RequestedItem(1, 2),
                        new RequestedItem(2, 2),
                        new RequestedItem(3, 5)
                )
        );

        orderService.create(orderReq);
        orderService.create(orderReq2);

    }

    @Transactional(readOnly = true)
    public Order getOrderById(int orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문이 존재하지 않습니다. id=" + orderId));
    }

    @Transactional
    public void work3(){

        Order order = getOrderById(1);
        orderService.delete(order);
    }

    @Transactional
    public void work4(){

        OrderModifyRequest orderReq = new OrderModifyRequest(
                List.of(
                        new RequestedItem(1, 77),
                        new RequestedItem(2, 77),
                        new RequestedItem(4, 77)
                )
        );

        Order order = getOrderById(1);

        orderService.modify(order, orderReq);
    }


}