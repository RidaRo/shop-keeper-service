package com.example.training.shop.keeper.controllers;

import com.example.training.shop.keeper.dto.ItemDTO;
import com.example.training.shop.keeper.models.Item;
import com.example.training.shop.keeper.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;

    @GetMapping("/items")
    public @ResponseBody
    Iterable<ItemDTO> getAllItems() {
        return itemService.findAll();
    }

    @PostMapping("/items")
    public @ResponseBody
    ItemDTO addItem(@RequestBody Item items) {
        return itemService.addItem(items);
    }

    @PutMapping("/items/{itemCode}")
    public @ResponseBody
    ItemDTO updateItem(@PathVariable Long itemCode, @RequestBody Item updatedItem) {
        return itemService.updateItem(itemCode, updatedItem);
    }

    @DeleteMapping("/items/{itemCode}")
    public @ResponseBody
    void deleteItem(@PathVariable Long itemCode) {
        itemService.deleteItem(itemCode);
    }

}
