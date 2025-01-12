package com.proyect.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MsUserServiceApplication {
	

	public static void main(String[] args) {
		SpringApplication.run(MsUserServiceApplication.class, args);
	}

	/*
	@Autowired
	UserRepository userRepository;
	
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
    */
}
