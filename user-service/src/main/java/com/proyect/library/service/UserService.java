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
import com.proyect.library.dto.CreateUserDTO;
import com.proyect.library.entity.ERole;
import com.proyect.library.entity.RoleEntity;
import com.proyect.library.entity.UserEntity;
@Service
public class UserService {
	
	@Autowired
	private PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    RoleRepository roleRepository;

    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }

    public UserEntity getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    
    public UserEntity createUser(CreateUserDTO createUserDto) {
    	 // Buscar roles existentes en la base de datos
	    Set<RoleEntity> roles = createUserDto.getRoles().stream()
	            .map(role -> {
	                ERole eRole = ERole.valueOf(role);
	                // Buscar el rol en la base de datos, si no existe, retornamos null
	                return roleRepository.findByName(eRole)
	                        .orElseGet(() -> RoleEntity.builder().name(eRole).build());
	            })
	            .collect(Collectors.toSet());

	    UserEntity userEntity = UserEntity.builder()
	            .username(createUserDto.getUsername())
	            .password(passwordEncoder.encode(createUserDto.getPassword())) 
	            .email(createUserDto.getEmail())
	            .roles(roles)
	            .build();

	    userRepository.save(userEntity);

	    return userEntity;
    }
    
    public String deleteUser(String id) {
		userRepository.deleteById(Long.parseLong(id));
		return "Se ha borrado el user con id ".concat(id);
	}
}