package com.corner.oauth.resource.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class UserController {

    @Autowired
    private OAuth2RestTemplate restTemplate;

    @GetMapping("/user")
    public Authentication user(Authentication user){
        return user;
    }

    @GetMapping("/test")
    public String test(){
        return "You Can Access This Resource";
    }

    @GetMapping("/test2")
    public String test2(){
        String result = restTemplate.getForObject("http://localhost:8085/resource2/test", String.class);
        return result;
    }
}
