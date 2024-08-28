package com.proyect.library.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
public class SecurityConfig {
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity/*, AuthenticationManager authenticationManager*/)
			throws Exception {
		
		return httpSecurity
		.csrf(config -> config.disable())
		.authorizeHttpRequests(auth -> {
			auth.anyRequest().permitAll(); // Requiere autenticación para cualquier otra solicitud
		})
		.build();
	}
}
