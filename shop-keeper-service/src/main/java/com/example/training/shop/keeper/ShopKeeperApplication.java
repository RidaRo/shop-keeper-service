package com.example.training.shop.keeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class ShopKeeperApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopKeeperApplication.class, args);
	}

}
