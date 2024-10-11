package com.proyect.library.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.proyect.library.repository.RoleRepository;
import com.proyect.library.repository.UserRepository;
import com.proyect.library.configuration.error.ResourceNotFoundException;
import com.proyect.library.model.dto.CreateUserRequestDto;
import com.proyect.library.model.dto.UserResponseDto;
import com.proyect.library.model.entity.ERole;
import com.proyect.library.model.entity.RoleEntity;
import com.proyect.library.model.entity.UserEntity;
import com.proyect.library.model.mapper.user.UserMapper;
@Service
public class UserService {
	
	@Autowired
	private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private UserMapper userMapper;

    public List<UserResponseDto> getAll() {
        List<UserEntity> userEntities = userRepository.findAll();
        List<UserResponseDto>  userDtos = userEntities.stream()
                .map(userMapper::entityToDto)
                .collect(Collectors.toList());
        if(userDtos.isEmpty()) {
            throw new ResourceNotFoundException("No users found");
        }
        return userDtos;
    }

    public UserResponseDto getUserById(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElse(null); // No lanzamos la excepción aquí
        // Convertimos a DTO
        UserResponseDto userDto = userMapper.entityToDto(userEntity);        
        // Lanza la excepción si el DTO es null
        if (userDto == null) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        return userDto;
    }

    public UserResponseDto createUser(CreateUserRequestDto createUserDto) {
        // Buscar roles existentes en la base de datos
        Set<RoleEntity> roles = createUserDto.getRoles().stream()
                .map(role -> {
                    ERole eRole = ERole.valueOf(role);
                    // Buscar el rol en la base de datos
                    return roleRepository.findByName(eRole)
                            .orElseThrow(() -> new ResourceNotFoundException("Role not found: " + role));
                })
                .collect(Collectors.toSet());

        // Crear el UserEntity
        UserEntity userEntity = UserEntity.builder()
                .username(createUserDto.getUsername())
                .password(passwordEncoder.encode(createUserDto.getPassword()))
                .email(createUserDto.getEmail())
                .roles(roles)
                .build();

        // Guardar el usuario en la base de datos
        userEntity = userRepository.save(userEntity);

        // Transformar a UserDto
        UserResponseDto userDto = userMapper.entityToDto(userEntity);

        // Lanza la excepción si el DTO es null (esto normalmente no debería ocurrir)
        if (userDto == null) {
            throw new ResourceNotFoundException("Failed to create user, user not found after saving.");
        }

        return userDto;
    }

    
    public UserResponseDto deleteUser(Long id) {
    	UserEntity userEntity = userRepository.findById(id)
                .orElse(null); // No lanzamos la excepción aquí
        // Convertimos a DTO
        UserResponseDto userDto = userMapper.entityToDto(userEntity);        
        // Lanza la excepción si el DTO es null
        if (userDto == null) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
		userRepository.deleteById(id);
		return userDto;
	}
}