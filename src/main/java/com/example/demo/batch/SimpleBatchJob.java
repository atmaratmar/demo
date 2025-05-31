package com.example.demo.batch;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

@Component
public class SimpleBatchJob implements CommandLineRunner {

    private final ApplicationContext context;

    public SimpleBatchJob(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void run(String... args) {
        System.out.println("Starting simple batch job...");
        System.out.println("test..");

        // Simulate batch logic
        try {
            Thread.sleep(2000); // simulate work
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Batch job finished.");

        // Shut down Spring Boot after job completes
        SpringApplication.exit(context, () -> 0);
    }
}
