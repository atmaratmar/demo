package com.example.demo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/atmar")
    public String home() {
        return "Spring Boot App is running inside Docker Swarm!";
    }
}