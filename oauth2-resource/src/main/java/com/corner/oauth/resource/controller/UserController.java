package com.corner.oauth.resource.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/user")
    public Authentication user(Authentication user){
        return user;
    }

    @GetMapping("/test")
    public String test(){
        return "You Can Access This Resource";
    }
}
