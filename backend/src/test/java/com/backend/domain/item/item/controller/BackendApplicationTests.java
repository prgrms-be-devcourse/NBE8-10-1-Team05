package com.backend.domain.item.item.controller;

import com.backend.domain.item.item.entity.Item;
import com.backend.domain.item.item.repository.ItemRepository;
import com.backend.domain.item.item.service.ItemService;

import com.backend.domain.order.order.dto.OrderCreateRequest;
import com.backend.domain.order.order.dto.RequestedItem;
import com.backend.domain.order.order.entity.Order;
import com.backend.domain.order.order.service.OrderService;
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
    @Autowired
    private OrderService orderService;

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
                    .andExpect(jsonPath("$.data[%d].id".formatted(i)).value(item.getId()))
                    .andExpect(jsonPath("$.data[%d].name".formatted(i)).value(item.getName()))
                    .andExpect(jsonPath("$.data[%d].category".formatted(i)).value(item.getCategory()))
                    .andExpect(jsonPath("$.data[%d].price".formatted(i)).value(item.getPrice()))
                    .andExpect(jsonPath("$.data[%d].imageUrl".formatted(i)).value(item.getImageUrl()));
        }
    }

    @Test
    @DisplayName("주문 생성 (POST)")
    void t7() throws Exception {
        // Given
        String email = "user1@example.com";
        String zipCode = "12345";
        String address = "서울시 강남구 테헤란로";
        int itemId = 1;
        int quantity = 3;

        // When
        ResultActions resultActions = mvc
                .perform(
                        post("/api/v1/order/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                            "email": "%s",
                                            "zipCode": "%s",
                                            "address": "%s",
                                            "items": [
                                                {
                                                    "itemId": %d,
                                                    "quantity": %d
                                                }
                                            ]
                                        }
                                        """.formatted(email, zipCode, address, itemId, quantity))
                                .characterEncoding(StandardCharsets.UTF_8)
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().isCreated())
                .andExpect(handler().methodName("create"))
                .andExpect(jsonPath("$.resultCode").value("201-1"))
                .andExpect(jsonPath("$.msg").value("주문이 완료되었습니다"))
                // OrderDtoMany 필드 검증
                .andExpect(jsonPath("$.data.id").exists()) // 주문 ID 생성 확인
                .andExpect(jsonPath("$.data.email").value(email))
                .andExpect(jsonPath("$.data.address").value(address));
    }

    @Test
    @DisplayName("주문 수정 (PUT)")
    void t8() throws Exception {
        // Given: 테스트를 위해 주문을 먼저 하나 생성합니다.
        String email = "user1@example.com";
        int itemId = 1; // 1번 상품은 존재한다고 가정 (BaseInitData)

        // 주문 생성 요청 객체 만들기 (Record 생성자 사용)
        // OrderCreateRequest(email, zipCode, address, items)
        OrderCreateRequest createReq = new OrderCreateRequest(
                email, "12345", "서울시 강남구",
                List.of(new RequestedItem(itemId, 3)) // 1번 상품 3개 주문
        );

        // 서비스로 주문 생성 후, 생성된 주문의 ID 가져오기
        Order order = orderService.create(createReq);
        long orderId = order.getId();

        // -------------------------------------------------------

        // When: 생성된 주문(orderId)의 내용을 수정합니다.
        int modifyItemId = 2; // 변경할 상품 ID (2번 상품으로 변경)
        int modifyQuantity = 10; // 수량 변경

        ResultActions resultActions = mvc
                .perform(
                        put("/api/v1/order/modify/" + orderId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                            "items": [
                                                {
                                                    "itemId": %d,
                                                    "quantity": %d
                                                }
                                            ]
                                        }
                                        """.formatted(modifyItemId, modifyQuantity))
                                .characterEncoding(StandardCharsets.UTF_8)
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(handler().methodName("modifyOrder"))
                .andExpect(jsonPath("$.resultCode").value("200-1"))
                .andExpect(jsonPath("$.msg").value("주문 수정이 완료되었습니다."));
    }

    @Test
    @DisplayName("주문 취소 (DELETE)")
    void t9() throws Exception {
        // Given: 취소할 주문을 먼저 생성
        String email = "cancel@test.com";
        int itemId = 1;
        OrderCreateRequest createReq = new OrderCreateRequest(
                email, "12345", "서울시 동작구",
                List.of(new RequestedItem(itemId, 5))
        );
        Order order = orderService.create(createReq);
        long orderId = order.getId();

        // When
        ResultActions resultActions = mvc
                .perform(
                        delete("/api/v1/order/cancel/" + orderId)
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(handler().methodName("cancelOrder"))
                .andExpect(jsonPath("$.resultCode").value("200-1"))
                .andExpect(jsonPath("$.msg").value("주문 취소가 완료되었습니다."));

    }

    @Test
    @DisplayName("이메일별 주문 다건 조회 (GET) - 주문 3개 생성")
    void t10() throws Exception {
        // Given
        String email = "list@test.com";
        int itemId = 1;

        // 주문 3개 생성 (반복문 사용)
        for (int i = 1; i <= 3; i++) {
            OrderCreateRequest createReq = new OrderCreateRequest(
                    email,
                    "12345",
                    "서울시 마포구 " + i + "번지", // 주소를 조금씩 다르게 설정
                    List.of(new RequestedItem(itemId, i)) // 수량도 1, 2, 3개로 설정
            );
            orderService.create(createReq);
        }

        // When
        ResultActions resultActions = mvc
                .perform(
                        get("/api/v1/order/listByEmail/" + email)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(handler().methodName("findByEmail"))
                .andExpect(jsonPath("$").isArray())
                // [중요] 리스트 크기가 3개인지 확인
                .andExpect(jsonPath("$.length()").value(3))
                // 첫 번째 주문 확인
                .andExpect(jsonPath("$[0].email").value(email))
                .andExpect(jsonPath("$[0].address").value("서울시 마포구 1번지"))
                // 마지막 주문 확인
                .andExpect(jsonPath("$[2].address").value("서울시 마포구 3번지"));
    }

    @Test
    @DisplayName("주문 상세 조회 (GET)")
    void t11() throws Exception {
        // Given: 상세 조회할 주문 생성
        String email = "detail@test.com";
        int itemId = 1;
        int quantity = 10;
        OrderCreateRequest createReq = new OrderCreateRequest(
                email, "12345", "서울시 서초구",
                List.of(new RequestedItem(itemId, quantity))
        );
        Order order = orderService.create(createReq);
        long orderId = order.getId();

        // When
        ResultActions resultActions = mvc
                .perform(
                        get("/api/v1/order/detail/" + orderId)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(handler().methodName("getOrderDetail"))
                // 상세 아이템 리스트(RequestedItem)가 배열로 반환됨
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].itemId").value(itemId))
                .andExpect(jsonPath("$[0].quantity").value(quantity));
    }
}