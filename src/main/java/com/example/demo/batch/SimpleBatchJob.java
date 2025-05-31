package com.example.demo.batch;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SimpleBatchJob implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Batch job started...");
        // Your batch logic here, e.g. process data, clean DB, etc.
        Thread.sleep(5000); // Simulate some work with 5 seconds delay
        System.out.println("Batch job finished.");
    }
}