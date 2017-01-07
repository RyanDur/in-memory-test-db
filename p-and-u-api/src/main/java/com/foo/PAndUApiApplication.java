package com.foo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude=FlywayAutoConfiguration.class)
public class PAndUApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PAndUApiApplication.class, args);
    }
}
