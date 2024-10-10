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
		            auth.requestMatchers("/auth/login").permitAll(); // Permitir acceso al login
		            auth.requestMatchers("/auth/validate").permitAll(); // Proteger validate
		            auth.anyRequest().permitAll(); // Permitir cualquier otra solicitud
		        })
		        .build();
	}
}
