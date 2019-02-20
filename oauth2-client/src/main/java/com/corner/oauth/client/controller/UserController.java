package com.corner.oauth.client.controller;

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

    @GetMapping("/testAuth")
    public String testAuth(){
        String result = restTemplate.getForObject("http://localhost:8084/resource/test", String.class);
        return result;
    }

    @GetMapping("/testAuth2")
    public String testAuth2(){
        String result = restTemplate.getForObject("http://localhost:8084/resource/test2", String.class);
        return result;
    }
}
