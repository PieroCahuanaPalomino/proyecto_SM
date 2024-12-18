package com.proyect.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableAdminServer
@SpringBootApplication
@EnableDiscoveryClient
public class MsSbaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsSbaServerApplication.class, args);
	}

}
