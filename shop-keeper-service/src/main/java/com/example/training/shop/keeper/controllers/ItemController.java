package com.example.training.shop.keeper.controllers;

import com.example.training.shop.keeper.dto.ItemDTO;
import com.example.training.shop.keeper.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
public class ItemController {
    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/items")
    public @ResponseBody Iterable<ItemDTO> getAllItems() {
        return itemService.findAll();
    }

    @GetMapping("/items/{itemCode}")
    public @ResponseBody ItemDTO getAllItems(@PathVariable UUID itemCode) {
        return itemService.findByCode(itemCode);
    }

    @PostMapping("/items")
    public @ResponseBody ItemDTO addItem(@RequestBody ItemDTO itemDTO) {
        return itemService.addItem(itemDTO.convertDTOToItem());
    }

    @PutMapping("/items/{itemCode}")
    public @ResponseBody ItemDTO updateItem(@PathVariable UUID itemCode, @RequestBody ItemDTO updatedItemDTO) {
        return itemService.updateItem(itemCode, updatedItemDTO.convertDTOToItem());
    }

    @DeleteMapping("/items/{itemCode}")
    public @ResponseBody void deleteItem(@PathVariable UUID itemCode) {
        itemService.deleteItem(itemCode);
    }

}
