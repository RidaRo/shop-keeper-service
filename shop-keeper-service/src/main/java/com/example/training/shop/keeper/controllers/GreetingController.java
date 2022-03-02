package com.example.training.shop.keeper.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GreetingController {

    @GetMapping("/")
    public @ResponseBody
    String greeting() {
        return "Hello, World";
    }

}