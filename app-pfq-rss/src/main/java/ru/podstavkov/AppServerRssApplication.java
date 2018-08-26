package ru.podstavkov;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableEurekaClient
public class AppServerRssApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(AppServerRssApplication.class, args);
        //ConfigurableApplicationContext ctx = new SpringApplication(AppServerRssApplication.class).run(args);
       // System.out.println("Press key to exit...");
       // System.in.read();
       // ctx.close();
	}
}
