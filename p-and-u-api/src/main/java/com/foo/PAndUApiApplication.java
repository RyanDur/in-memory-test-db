package com.foo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableAutoConfiguration(exclude=FlywayAutoConfiguration.class)
public class PAndUApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PAndUApiApplication.class, args);
    }
}
