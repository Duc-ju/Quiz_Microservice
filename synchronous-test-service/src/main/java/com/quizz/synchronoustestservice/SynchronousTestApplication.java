package com.quizz.synchronoustestservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SynchronousTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SynchronousTestApplication.class, args);
    }
}
