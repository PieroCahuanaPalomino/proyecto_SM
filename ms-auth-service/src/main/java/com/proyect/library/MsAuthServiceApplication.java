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
	
	@Autowired
	AuthUserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Bean
	CommandLineRunner init() {
		return args -> {
			UserEntity userEntity = UserEntity.builder()
					.email("santiago.mendez@unmsm.edu.pe")
					.username("Santiago.mendez")
					.password(passwordEncoder.encode("77810931"))
					.roles(Set.of(RoleEntity.builder().name(ERole.valueOf(ERole.ADMIN.name())).build())).build();

			UserEntity userEntity2 = UserEntity.builder()
					.email("liza.vidal@unmsm.edu.pe")
					.username("Liza.vidal")
					.password(passwordEncoder.encode("77810931"))
					.roles(Set.of(RoleEntity.builder().name(ERole.valueOf(ERole.USER.name())).build())).build();

			UserEntity userEntity3 = UserEntity.builder()
					.email("marcos.herrera@unmsm.edu.pe")
					.username("Marcos.herrera")
					.password(passwordEncoder.encode("77810931"))
					.roles(Set.of(RoleEntity.builder().name(ERole.valueOf(ERole.INVITED.name())).build())).build();

			userRepository.save(userEntity);
			userRepository.save(userEntity2);
			userRepository.save(userEntity3);
		};
	}

}
