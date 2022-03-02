package com.example.training.shop.keeper.controllers;

import com.example.training.shop.keeper.models.Item;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemController.class)
class ItemControllerTest extends KeeperControllerTest{
    private final static Item testItemObject = new Item();
    private final static String JSONTestItem = "{\"id\":1,\"name\":\"broom\",\"price\":100,\"quantity\":10}";

    @BeforeAll
    public static void setup(){
        testItemObject.setId(1L);
        testItemObject.setName("broom");
        testItemObject.setPrice(100L);
        testItemObject.setQuantity(10L);
    }

    @Test
    void successfulGetAllItems() throws Exception {
        when(mockedItemService.findAll()).thenReturn(List.of(testItemObject));

        this.mockMvc.perform(get("/items"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(JSONTestItem)));
    }

    @Test
    void successfulAddItem() throws Exception{
        when(mockedItemService.addItem(testItemObject)).thenReturn(testItemObject);

        this.mockMvc.perform(post("/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONTestItem))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(JSONTestItem)));
    }

    @Test
    void successfulUpdateItem() throws Exception{
        when(mockedItemService.updateItem(testItemObject.getId(), testItemObject)).thenReturn(testItemObject);
        this.mockMvc.perform(put("/items/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONTestItem))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(JSONTestItem)));
    }

    @Test
    void successfulDeleteItem() throws Exception{
        this.mockMvc.perform(delete("/items/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
