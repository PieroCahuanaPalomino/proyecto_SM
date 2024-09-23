package com.proyect.library.service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.proyect.library.dto.AuthUserDto;
import com.proyect.library.dto.RequestDto;
import com.proyect.library.dto.TokenDto;
import com.proyect.library.entity.UserEntity;
import com.proyect.library.entity.RoleEntity;
import com.proyect.library.entity.ERole;
import com.proyect.library.repository.AuthUserRepository;
import com.proyect.library.security.JwtProvider;
import com.proyect.library.security.RouteValidator;

import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class AuthService {
	@Autowired
	RouteValidator routeValidator;
	
	@Autowired
	AuthUserRepository authUserRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	JwtProvider jwtProvider;
	
	
	public TokenDto login(AuthUserDto dto) {
		Optional<UserEntity> user = authUserRepository.findByUsername(dto.getUsername());
		if(!user.isPresent()) {
			return null;
		}
		if(passwordEncoder.matches(dto.getPassword(), user.get().getPassword())) {
			return new TokenDto(jwtProvider.createToken(user.get()));
		}
		return null;
	}
	
	public TokenDto validate(String token, RequestDto dto) {
		if(!jwtProvider.validate(token)) {
			log.info("AQUI ENTRA 1");
            return null; // Manejo de errores a mejorar
		}
		String username = jwtProvider.getUserNameFromToken(token);
        Optional<UserEntity> user = authUserRepository.findByUsername(username);
        if (!user.isPresent()) {
			log.info("AQUI ENTRA 2");
            return null; // Manejo de errores a mejorar
        }

		// Comprobar roles del usuario y acceso a la ruta
        Set<ERole> userRoles = user.get().getRoles().stream().map(RoleEntity::getName).collect(Collectors.toSet());
        if (!routeValidator.isAuthorized(dto.getMethod(), dto.getUri(), userRoles)) {
			log.info("AQUI ENTRA 3");
            return null; // No autorizado
        }
		return new TokenDto(token);
	}
}
