package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
//@EnableDiscoveryClient
public class RabbitMqProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitMqProducerApplication.class, args);
    }

}
