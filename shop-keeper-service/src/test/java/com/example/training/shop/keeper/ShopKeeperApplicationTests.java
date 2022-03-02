package com.example.training.shop.keeper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;

@SpringBootTest
@ActiveProfiles("test")
class ShopKeeperApplicationTests {

    @Test
    void contextLoads() {
        Assert.isTrue(true);
    }

}
