package com.backend.domain.item.item.controller;

import com.backend.domain.item.item.dto.ItemResponse;
import com.backend.domain.item.item.entity.Item;
import com.backend.domain.item.item.service.ItemService;
import com.backend.global.rsData.RsData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/item/list")
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    @Transactional(readOnly = true)
    public RsData<List<Item>> getItemList() {
        List<Item> items = itemService.findAll();

        return new RsData<>(
                "200-1",
                "전체 상품 조회가 완료되었습니다.",
                items
        );
    }

    record ItemCreateRequest(
            String name,
            int price,
            String category,
            String imageUrl
    ) {
    }

    @PostMapping
    public RsData<Item> createItem(@RequestBody @Valid ItemCreateRequest Request) {

        Item item = itemService.createItem(
                Request.name(),
                Request.category(),
                Request.price(),
                Request.imageUrl());

        return new RsData<>(
                "201-1",
                "%d번 상품이 등록되었습니다.".formatted(item.getId()),
                item
        );
    }

    record ItemModifyRequest(
            String name,
            int price,
            String category,
            String imageUrl
    ) {
    }

    // 단건 조회 (GET)
    @GetMapping("/{id}")
    public RsData<ItemResponse> getItem(@PathVariable Integer id) {
        Item item = itemService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("%d번 상품이 없습니다.".formatted(id)));

        return new RsData<>(
                "200-1",
                "%d번 상품을 조회합니다.".formatted(id),
                new ItemResponse(item)
        );
    }

    @PutMapping("{id}")
    public RsData<Item> modifyItem(@PathVariable Integer id, @RequestBody ItemModifyRequest request){
        Item item = itemService.modifyItem(id,request.name(),request.category(),request.price(),request.imageUrl());
        return new RsData<>(
                "200-1",
                "%d번 상품이 수정되었습니다".formatted(id),item
        );
    }

    @DeleteMapping("/{id}")
    public RsData<Void> deleteItem(@PathVariable Integer id){
        itemService.deleteItem(id);

        return new RsData<>(
                "200-1",
                "%d번 상품이 삭제되었습니다.".formatted(id)
        );
    }
}
