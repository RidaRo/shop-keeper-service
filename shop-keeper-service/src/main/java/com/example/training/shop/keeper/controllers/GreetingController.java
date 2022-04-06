package com.example.training.shop.keeper.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
public class GreetingController {

    @GetMapping("/")
    @RolesAllowed("items_read")
    public @ResponseBody
    String greeting() {
        return "Hello, World";
    }

}