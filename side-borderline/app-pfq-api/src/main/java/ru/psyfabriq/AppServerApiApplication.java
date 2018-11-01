package ru.psyfabriq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class AppServerApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(AppServerApiApplication.class, args);
    }
}
