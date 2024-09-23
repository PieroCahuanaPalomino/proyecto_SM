package com.proyect.library;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.proyect.library.entity.ERole;
import com.proyect.library.entity.RoleEntity;
import com.proyect.library.entity.UserEntity;
import com.proyect.library.repository.AuthUserRepository;

@EnableDiscoveryClient
@SpringBootApplication
public class MsAuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsAuthServiceApplication.class, args);
	}

}
