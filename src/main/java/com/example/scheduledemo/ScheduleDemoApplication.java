package com.example.scheduledemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ScheduleDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScheduleDemoApplication.class, args);
    }

}
