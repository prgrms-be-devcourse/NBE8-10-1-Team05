package com.backend.global.initData;

import com.backend.domain.item.item.service.ItemService;
import com.backend.domain.order.order.controller.OrderController;
import com.backend.domain.order.order.dto.OrderCreateRequest;
import com.backend.domain.order.order.dto.OrderModifyRequest;
import com.backend.domain.order.order.dto.RequestedItem;
import com.backend.domain.order.order.repository.OrderRepository;
import com.backend.domain.order.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;
import com.backend.domain.order.order.entity.Order;

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
    private final OrderController orderController;

    @Bean
    ApplicationRunner baseInitDataApplicationRunner() {
        return args -> {
            self.work1();
            //self.work2();
            //self.work4();
            //self.work3();
        };
    }

    @Transactional
    public void work1() {
        //System.out.println("work1, 아이템 완성된 후, itemService 사용해서 Item db에 추가해주세요\n");
        itemService.createItem("Columbia Nariño", "커피콩", 5000, "image/Columbia_Nariño.png");
        itemService.createItem("Brazil Serra Do Caparaó", "커피콩", 6000, "image/Brazil_Serra_Do_Caparaó.png");
        itemService.createItem("Columbia Quindío (White Wine Extended Fermentation)", "커피콩", 7000, "image/Columbia_Quindío.png");
        itemService.createItem("Ethiopia Sidamo", "커피콩", 8000, "image/Ethiopia_Sidamo.png");


        //TODO item 추가 된 후, 샘플 주문 데이터도 db에 추가해주세요
    }

    @Transactional
    public void work2() {
        OrderCreateRequest orderReq = new OrderCreateRequest(
                "bbb@test.com",
                "77777",
                "후쿠오카",
                List.of(
                        new RequestedItem(1, 2),
                        new RequestedItem(2, 1),
                        new RequestedItem(4, 3)
                )
        );

        orderService.create(orderReq);
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
                        new RequestedItem(1, 777),
                        new RequestedItem(2, 777),
                        new RequestedItem(4, 777)
                )
        );

        Order order = getOrderById(1);

        orderService.modify(order, orderReq);
    }


}