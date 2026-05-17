package com.tuoguan.pickup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class PickupSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(PickupSystemApplication.class, args);
    }
}