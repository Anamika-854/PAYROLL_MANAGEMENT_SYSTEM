package com.hexaware.easypay.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/api/v1/auth/public")
    public String publicEndpoint() {
        return "This is a public endpoint";
    }

    @GetMapping("/api/v1/auth/private")
    public String privateEndpoint() {
        return "This is a private endpoint";
    }
}