package com.proyect.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MsAuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsAuthServiceApplication.class, args);
	}

}
