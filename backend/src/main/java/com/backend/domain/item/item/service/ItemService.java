package com.backend.domain.item.item.service;

import com.backend.domain.item.item.entity.Item;
import com.backend.domain.item.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    @Transactional
    public Item createItem(String name, String category, int price, String imageUrl) {
        Item item = new Item(name, category, price, imageUrl);
        return itemRepository.save(item);
    }

    @Transactional(readOnly = true)
    public List<Item> getItemList(){
        return itemRepository.findAll();
    }

    @Transactional
    public Item modifyItem(Integer id, String name, String category, int price, String imageUrl) {
        // .orElseThrow()로 Optional 껍질을 벗겨줍니다.
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이템이 없습니다. id=" + id));

        item.modify(name, category, price, imageUrl);

        return item;
    }

    @Transactional
    public void deleteItem(Integer id) {
        itemRepository.deleteById(id);
    }
}
