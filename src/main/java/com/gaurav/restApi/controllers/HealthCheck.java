package com.gaurav.restApi.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("health-check")
public class HealthCheck {
    
    @GetMapping()
    public String getMethodName() {
        return new String("Spring Boot server is running OK");
    }
    
}
