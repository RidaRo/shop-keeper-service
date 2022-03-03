package com.example.training.shop.keeper.controllers;

import com.example.training.shop.keeper.dto.ItemDTO;
import com.example.training.shop.keeper.models.Item;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ItemControllerTest extends KeeperControllerTest {
    private final static ItemDTO testItemDTOObject = new ItemDTO();
    private final static Item testItemObject = new Item();
    private final static String JSONTestItemDTO = "[{\"code\":1,\"name\":\"broom\",\"price\":10,\"quantity\":10}]";
    private final static String JSONTestItem = "{\"code\":1,\"name\":\"broom\",\"price\":10,\"quantity\":10}";

    @BeforeAll
    public static void setup() {
        testItemObject.setId(1L);
        testItemObject.setCode(1L);
        testItemObject.setName("broom");
        testItemObject.setPrice(new BigDecimal(10));
        testItemObject.setQuantity(10L);

        testItemDTOObject.setCode(1L);
        testItemDTOObject.setName("broom");
        testItemDTOObject.setPrice(new BigDecimal(10));
        testItemDTOObject.setQuantity(10L);
    }

    @Test
    void successfulGetAllItems() throws Exception {
        when(mockedItemService.findAll())
                .thenReturn(List.of(testItemDTOObject));

        this.mockMvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(JSONTestItemDTO)));
    }

    @Test
    void successfulAddItem() throws Exception {
        when(mockedItemService.addItem(any()))
                .thenReturn(testItemDTOObject);

        this.mockMvc.perform(post("/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONTestItem))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(JSONTestItem)));
    }

    @Test
    void successfulUpdateItem() throws Exception {
        when(mockedItemService.updateItem(anyLong(), any()))
                .thenReturn(testItemDTOObject);
        this.mockMvc.perform(put("/items/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONTestItem))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(JSONTestItem)));
    }

    @Test
    void successfulDeleteItem() throws Exception {
        this.mockMvc.perform(delete("/items/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
