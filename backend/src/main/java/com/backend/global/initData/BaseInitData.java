package com.backend.global.initData;

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
    //private final ItemService itemService;
    //private final OrderService orderService;

    @Bean
    ApplicationRunner baseInitDataApplicationRunner() {
        return args -> {
            self.work1();
        };
    }

    @Transactional
    public void work1() {
        System.out.println("work1, 아이템 완성된 후, itemService 사용해서 Item db에 추가해주세요\n");
        //TODO itemService 사용해서 Item db에 추가해주세요

        //TODO item 추가 된 후, 샘플 주문 데이터도 db에 추가해주세요
    }
}