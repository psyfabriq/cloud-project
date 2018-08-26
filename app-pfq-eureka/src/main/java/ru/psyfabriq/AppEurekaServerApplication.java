package ru.psyfabriq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class AppEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppEurekaServerApplication.class, args);
	}
}
