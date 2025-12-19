package com.backend.domain.item.item.controller;

import com.backend.domain.item.item.entity.Item;
import com.backend.domain.item.item.repository.ItemRepository;
import com.backend.domain.item.item.service.ItemService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class BackendApplicationTests {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemService itemService;

    @Test
    @DisplayName("상품 등록 (POST)")
    void t1() throws Exception {

        String name = "에티오피아 예가체프 G1";
        int price = 12000;
        String category = "커피콩";
        String imageUrl = "image/Ethiopia_Yirgacheffe.png";


        ResultActions resultActions = mvc
                .perform(
                        post("/api/v1/item/list") // 컨트롤러 경로 확인 (/api/v1/items)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                            "name": "%s",
                                            "price": %d,
                                            "category": "%s",
                                            "imageUrl": "%s"
                                        }
                                        """.formatted(name, price, category, imageUrl))
                                .characterEncoding(StandardCharsets.UTF_8)
                )
                .andDo(print());

        resultActions
                .andExpect(handler().handlerType(ItemController.class))
                .andExpect(handler().methodName("createItem"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.resultCode").value("201-1"))
                .andExpect(jsonPath("$.msg").exists())
                .andExpect(jsonPath("$.data.name").value(name))
                .andExpect(jsonPath("$.data.price").value(price))
                .andExpect(jsonPath("$.data.category").value(category));


        List<Item> allItems = itemRepository.findAll();
        Item lastItem = allItems.get(allItems.size() - 1);

        assertThat(lastItem.getName()).isEqualTo(name);
    }

    @Test
    @DisplayName("상품 전체 조회 (GET)")
    void t2() throws Exception {
        // When
        ResultActions resultActions = mvc
                .perform(
                        get("/api/v1/item/list")
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(handler().handlerType(ItemController.class))
                .andExpect(handler().methodName("getItemList"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].name").exists())
                .andExpect(jsonPath("$.data[0].price").exists());
    }


    @Test
    @DisplayName("상품 수정 (PUT)")
    void t3() throws Exception {
        // Given
        Integer id = 1;
        String modifyName = "수정된 에티오피아 원두";
        int modifyPrice = 50000;

        // When
        ResultActions resultActions = mvc
                .perform(
                        put("/api/v1/item/list/" + id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                            "name": "%s",
                                            "price": %d,
                                            "category": "원두",
                                            "imageUrl": "image/new.png"
                                        }
                                        """.formatted(modifyName, modifyPrice))
                                .characterEncoding(StandardCharsets.UTF_8)
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(handler().handlerType(ItemController.class))
                .andExpect(handler().methodName("modifyItem"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value("200-1"))
                .andExpect(jsonPath("$.msg").value("%d번 상품이 수정되었습니다".formatted(id)))
                .andExpect(jsonPath("$.data.name").value(modifyName))
                .andExpect(jsonPath("$.data.price").value(modifyPrice));
    }

    @Test
    @DisplayName("상품 삭제 (DELETE)")
    void t4() throws Exception {
        // Given
        Integer id = 1;

        // When
        ResultActions resultActions = mvc
                .perform(
                        delete("/api/v1/item/list/" + id)
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(handler().handlerType(ItemController.class))
                .andExpect(handler().methodName("deleteItem"))
                .andExpect(status().isOk()) // 혹은 204 No Content
                .andExpect(jsonPath("$.resultCode").value("200-1"))
                .andExpect(jsonPath("$.msg").value("%d번 상품이 삭제되었습니다.".formatted(id)));

        // 실제 삭제 확인
        boolean exists = itemRepository.existsById(id);
        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("상품 단건 조회")
    void t5() throws Exception {
        Integer id = 1;

        ResultActions resultActions = mvc
                .perform(
                        get("/api/v1/item/list/" + id)
                )
                .andDo(print());

        Item item = itemService.findById(id).get();

        resultActions
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(ItemController.class))
                .andExpect(handler().methodName("getItem"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value(item.getName()))
                .andExpect(jsonPath("$.data.category").value(item.getCategory()))
                .andExpect(jsonPath("$.data.price").value(item.getPrice()))
                .andExpect(jsonPath("$.data.imageUrl").value(item.getImageUrl()));
    }

    @Test
    @DisplayName("상품 다건 조회")
    void t6() throws Exception {

        ResultActions resultActions = mvc
                .perform(
                        get("/api/v1/item/list")
                )
                .andDo(print());

        List<Item> items = itemRepository.findAll();

        resultActions
                .andExpect(handler().handlerType(ItemController.class))
                .andExpect(handler().methodName("getItemList"))
                .andExpect(status().isOk());

        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            resultActions
                    .andExpect(jsonPath("$.data[%d].id".formatted(i)).value(item.getId().intValue()))
                    .andExpect(jsonPath("$.data[%d].name".formatted(i)).value(item.getName()))
                    .andExpect(jsonPath("$.data[%d].category".formatted(i)).value(item.getCategory()))
                    .andExpect(jsonPath("$.data[%d].price".formatted(i)).value(item.getPrice()))
                    .andExpect(jsonPath("$.data[%d].imageUrl".formatted(i)).value(item.getImageUrl()));
        }
    }
}