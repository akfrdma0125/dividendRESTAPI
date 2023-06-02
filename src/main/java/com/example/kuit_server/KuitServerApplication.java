package com.example.kuit_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KuitServerApplication {

    public static void main(String[] args) {
        System.setProperty("spring.profiles.default","prod");
        SpringApplication.run(KuitServerApplication.class, args);
    }

}
