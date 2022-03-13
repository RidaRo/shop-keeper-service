package com.example.training.shop.keeper.dto;

import com.example.training.shop.keeper.models.Item;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ItemDTO {
    private UUID code;
    private String name;
    private BigDecimal price;
    private Long quantity;

    public Item convertDTOToItem(){
        Item item = new Item();
        item.setName(name);
        item.setPrice(price);
        item.setQuantity(quantity);
        item.setCode(UUID.randomUUID());
        return item;
    }
}
