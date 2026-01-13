package com.lumen.minuAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MinuApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MinuApiApplication.class, args);
	}

}
