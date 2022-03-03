package com.example.training.shop.keeper.repositories;

import com.example.training.shop.keeper.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findByCode(Long code);
}
