package ru.psyfabriq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class AppServerAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AppServerAuthApplication.class, args);
    }
}
