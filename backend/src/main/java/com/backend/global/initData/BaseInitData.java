package com.backend.global.initData;

import com.backend.domain.item.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@RequiredArgsConstructor
public class BaseInitData {
    @Autowired
    @Lazy
    private BaseInitData self;
    private final ItemService itemService;
    //private final OrderService orderService;

    @Bean
    ApplicationRunner baseInitDataApplicationRunner() {
        return args -> {
            self.work1();
        };
    }

    @Transactional
    public void work1() {
        //System.out.println("work1, 아이템 완성된 후, itemService 사용해서 Item db에 추가해주세요\n");
        //TODO itemService 사용해서 Item db에 추가해주세요
        itemService.createItem("Columbia Nariño", "커피콩", 5000, "image/Columbia_Nariño.png");
        itemService.createItem("Brazil Serra Do Caparaó", "커피콩", 6000, "image/Brazil_Serra_Do_Caparaó.png");
        itemService.createItem("Columbia Quindío (White Wine Extended Fermentation)", "커피콩", 7000, "image/Columbia_Quindío.png");
        itemService.createItem("Ethiopia Sidamo", "커피콩", 8000, "image/Ethiopia_Sidamo.png");


        //TODO item 추가 된 후, 샘플 주문 데이터도 db에 추가해주세요
    }
}