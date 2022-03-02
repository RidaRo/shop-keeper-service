package com.example.training.shop.keeper.controllers;

import com.example.training.shop.keeper.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
@ActiveProfiles("test")
public class KeeperControllerTest {
    /////////////////////////////////////////////////////////////////////
    //// Main controller test class, every test for controllers must ////
    //// extend from it. All mocked objects should be defined here   ////
    /////////////////////////////////////////////////////////////////////

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    protected ItemService mockedItemService;
}
