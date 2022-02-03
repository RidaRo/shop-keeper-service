package com.example.training.shop.keeper.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long quantity;
}
