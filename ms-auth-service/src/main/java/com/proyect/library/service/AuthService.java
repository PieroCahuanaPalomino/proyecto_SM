package com.proyect.library.service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.proyect.library.configuration.Error.ResourceNotFoundException;
import com.proyect.library.model.dto.AuthUserDto;
import com.proyect.library.model.dto.RequestDto;
import com.proyect.library.model.dto.TokenDto;
import com.proyect.library.model.dto.UserDto;
import com.proyect.library.model.entity.ERole;
import com.proyect.library.model.entity.RoleEntity;
import com.proyect.library.model.entity.UserEntity;
import com.proyect.library.model.mapper.user.UserMapper;
import com.proyect.library.repository.AuthUserRepository;
import com.proyect.library.security.JwtProvider;
import com.proyect.library.security.RouteValidator;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class AuthService {
	@Autowired
	private RouteValidator routeValidator;
	
	@Autowired
	private AuthUserRepository authUserRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtProvider jwtProvider;
	
    @Autowired
    private UserMapper userMapper;
	
    public TokenDto login(AuthUserDto dto) {
        Optional<UserEntity> userOptional = authUserRepository.findByUsername(dto.getUsername());
        
        // Comprobar si el usuario existe
        if (!userOptional.isPresent()) {
            throw new ResourceNotFoundException("User not found with username: " + dto.getUsername());
        }
        
        UserEntity user = userOptional.get();
        
        // Verificar la contraseña
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid password for user: " + dto.getUsername());
        }
        
        // Convertir UserEntity a UserDto usando el mapper
        UserDto userDto = userMapper.entityToDto(user);

        // Crear el token usando UserDto
        return new TokenDto(jwtProvider.createToken(userDto));
    }

	
	public TokenDto validate(String token, RequestDto dto) {
		if(!jwtProvider.validate(token)) {
			log.info("AQUI ENTRA 1");
            throw new IllegalArgumentException("Invalid token");
		}
		String username = jwtProvider.getUserNameFromToken(token);
        Optional<UserEntity> user = authUserRepository.findByUsername(username);
        if (!user.isPresent()) {
			log.info("AQUI ENTRA 2");
            throw new ResourceNotFoundException("User not found with username: " + username);
        }

		// Comprobar roles del usuario y acceso a la ruta
        Set<ERole> userRoles = user.get().getRoles().stream().map(RoleEntity::getName).collect(Collectors.toSet());
        if (!routeValidator.isAuthorized(dto.getMethod(), dto.getUri(), userRoles)) {
			log.info("AQUI ENTRA 3");
            throw new SecurityException("User not authorized");
        }
		return new TokenDto(token);
	}
}
