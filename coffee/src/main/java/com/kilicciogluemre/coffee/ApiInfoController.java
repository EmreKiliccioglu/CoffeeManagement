package com.kilicciogluemre.coffee;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api-info")
public class ApiInfoController {

    @GetMapping
    public Map<String, String> info(){
        return Map.of(
                "application", "Coffee Backend API",
                "version", "1.0.0",
                "swagger", "http://localhost:8080/swagger-ui/index.html"
        );
    }
}
