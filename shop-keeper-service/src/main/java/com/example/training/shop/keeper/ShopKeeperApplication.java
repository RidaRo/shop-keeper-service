package com.example.training.shop.keeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class ShopKeeperApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopKeeperApplication.class, args);
    }

}
