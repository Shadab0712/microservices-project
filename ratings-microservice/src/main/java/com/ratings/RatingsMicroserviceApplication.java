package com.ratings;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class RatingsMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RatingsMicroserviceApplication.class, args);
	}

}
