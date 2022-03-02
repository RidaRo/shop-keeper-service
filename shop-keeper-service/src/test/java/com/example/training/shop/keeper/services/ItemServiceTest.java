package com.example.training.shop.keeper.services;

import com.example.training.shop.keeper.models.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Sql(value = {"/create-item-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/delete-item-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class ItemServiceTest {

    @Autowired
    private ItemService itemService;


    @Test
    void successfulFindAll() {
        List<Item> itemList = itemService.findAll();
        assertFalse(itemList.isEmpty());
    }

    @Test
    void successfulAddItem() {
        List<Item> itemList = itemService.findAll();
        assertEquals(3, itemList.size());

        Item item = new Item();
        item.setName("testItem");
        item.setPrice(0L);
        item.setQuantity(1L);

        itemService.addItem(item);
        itemList = itemService.findAll();
        assertEquals(4, itemList.size());
        assertTrue(itemList.contains(item));
    }

    @Test
    void updateItem() {
        Item item = new Item();
        item.setName("testItem");
        item.setPrice(0L);
        item.setQuantity(1L);

        List<Item> originalItems = itemService.findAll();

        Item updatedItem = itemService.updateItem(1L, item);

        assertEquals(originalItems.size(), itemService.findAll().size());
        assertTrue(itemService.findAll().contains(updatedItem));
    }

    @Test
    void deleteItem() {
        List<Item> items = itemService.findAll();
        items.remove(0);

        itemService.deleteItem(1L);

        assertEquals(2, itemService.findAll().size());
        assertEquals(items, itemService.findAll());
    }
}