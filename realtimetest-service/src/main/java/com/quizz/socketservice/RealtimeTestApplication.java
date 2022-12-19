package com.quizz.socketservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class RealtimeTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(RealtimeTestApplication.class, args);
    }
}
