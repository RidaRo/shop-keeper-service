package com.example.training.shop.keeper.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemDTO {
    private Long code;
    private String name;
    private BigDecimal price;
    private Long quantity;
}
