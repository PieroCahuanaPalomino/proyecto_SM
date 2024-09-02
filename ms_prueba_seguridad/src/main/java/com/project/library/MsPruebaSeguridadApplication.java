package com.project.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MsPruebaSeguridadApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsPruebaSeguridadApplication.class, args);
	}

}
