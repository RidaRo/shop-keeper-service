package com.example.training.shop.keeper.services;

import com.example.training.shop.keeper.models.Item;
import com.example.training.shop.keeper.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public Item addItem(Item item) {
        return itemRepository.save(item);
    }

    public Item updateItem(Long itemCode, Item updatedItem) {
        if (itemRepository.findById(itemCode).isPresent()) {
            return itemRepository.save(updatedItem);
        } else {
            throw new IllegalArgumentException("Item does not exist");
        }
    }

    public void deleteItem(Long itemCode){
        itemRepository.deleteById(itemCode);
    }

}
