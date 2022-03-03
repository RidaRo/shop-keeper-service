package com.example.training.shop.keeper.services;

import com.example.training.shop.keeper.dto.ItemDTO;
import com.example.training.shop.keeper.models.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
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
        List<ItemDTO> itemList = itemService.findAll();
        assertFalse(itemList.isEmpty());
        assertEquals(3, itemList.size());
    }

    @Test
    void successfulAddItem() {
        List<ItemDTO> itemList = itemService.findAll();
        assertEquals(3, itemList.size());

        Item item = new Item();
        item.setCode(31L);
        item.setName("testItem");
        item.setPrice(new BigDecimal(10));
        item.setQuantity(1L);

        itemService.addItem(item);
        itemList = itemService.findAll();
        assertEquals(4, itemList.size());
        assertTrue(itemList.contains(itemService.convertEntityToDTO(item)));
    }

    @Test
    void updateItem() {
        Item item = new Item();
        item.setName("testItem");
        item.setPrice(new BigDecimal(10));
        item.setQuantity(1L);

        List<ItemDTO> originalItems = itemService.findAll();

        ItemDTO updatedItem = itemService.updateItem(originalItems.get(0).getCode(), item);

        assertEquals(originalItems.size(), itemService.findAll().size());
        assertTrue(itemService.findAll().contains(updatedItem));
    }

    @Test
    void deleteItem() {
        List<ItemDTO> originalItems = itemService.findAll();

        itemService.deleteItem(originalItems.get(0).getCode());
        originalItems.remove(0);

        assertEquals(2, itemService.findAll().size());
        assertEquals(originalItems, itemService.findAll());
    }
}