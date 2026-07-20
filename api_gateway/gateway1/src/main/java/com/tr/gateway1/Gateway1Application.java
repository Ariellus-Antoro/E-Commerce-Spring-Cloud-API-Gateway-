package com.tr.gateway1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class Gateway1Application {

	public static void main(String[] args) {
		SpringApplication.run(Gateway1Application.class, args);
	}

}
