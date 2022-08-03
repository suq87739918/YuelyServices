package com.try07.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class Try07Application {
	public static void main(String[] args) {
		SpringApplication.run(Try07Application.class, args);
	}
}
