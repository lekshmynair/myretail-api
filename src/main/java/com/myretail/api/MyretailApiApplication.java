package com.myretail.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MyretailApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(MyretailApiApplication.class, args);
	}
}
