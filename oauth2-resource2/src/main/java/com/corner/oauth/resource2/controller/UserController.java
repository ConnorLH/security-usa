package com.corner.oauth.resource2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/test")
    public String test(){
        return "You Can Pass Through Controllered Resource Access This Resource";
    }
}
