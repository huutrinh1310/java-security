package com.example.demo.service.impl;

import com.example.demo.model.persistence.Item;
import com.example.demo.repository.ItemRepository;
import com.example.demo.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;

    @Override
    public List<Item> getItems() {
        return itemRepository.findAll();
    }

    @Override
    public Item getItemById(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new RuntimeException("Item not found"));
        return item;
    }

    @Override
    public List<Item> getItemsByName(String name) {
        return itemRepository.findByName(name);
    }
}
