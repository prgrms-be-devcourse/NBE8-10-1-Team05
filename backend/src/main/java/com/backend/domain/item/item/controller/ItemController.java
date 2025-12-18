package com.backend.domain.item.item.controller;

import com.backend.domain.item.item.dto.ItemResponse;
import com.backend.domain.item.item.entity.Item;
import com.backend.domain.item.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/item/list")
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public List<ItemResponse> getItemList() {
        return itemService.getItemList()
                .stream()
                .map(ItemResponse::from)
                .toList();
    }

    record ItemCreateRequest(
            String name,
            int price,
            String category,
            String imageUrl
    ) {
    }

    @PostMapping
    public ItemResponse createItem(@RequestBody ItemCreateRequest request){
        Item item = itemService.createItem(
                request.name(),
                request.category(),
                request.price(),
                request.imageUrl()
        );
        return ItemResponse.from(item);
    }

    record ItemModifyRequest(
            String name,
            int price,
            String category,
            String imageUrl
    ) {
    }

    @PutMapping("{id}")
    public ItemResponse modifyItem(@PathVariable Integer id, @RequestBody ItemModifyRequest request){
        Item item = itemService.modifyItem(id,request.name(),request.category(),request.price(),request.imageUrl());
        return ItemResponse.from(item);


    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Integer id){
        itemService.deleteItem(id);
    }
}
