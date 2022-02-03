package com.example.training.shop.keeper.controllers;

import com.example.training.shop.keeper.models.Item;
import com.example.training.shop.keeper.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;

    @GetMapping("/items")
    public @ResponseBody Iterable<Item> getAllItems(){
        return itemService.findAll();
    }
}
