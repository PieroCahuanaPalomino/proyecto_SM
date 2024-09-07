package com.proyect.library.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.proyect.library.dto.AuthUserDto;
import com.proyect.library.dto.NewUserDto;
import com.proyect.library.dto.RequestDto;
import com.proyect.library.dto.TokenDto;
import com.proyect.library.entity.AuthUser;
import com.proyect.library.repository.AuthUserRepository;
import com.proyect.library.security.JwtProvider;

@Service
public class AuthService {
	@Autowired
	AuthUserRepository authUserRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	JwtProvider jwtProvider;
	
	public AuthUser save(NewUserDto dto) {
		Optional<AuthUser> user = authUserRepository.findByUsername(dto.getUsername());
		if(user.isPresent()) {
			return null;
		}
		
		String password = passwordEncoder.encode(dto.getPassword());
		AuthUser authUser = AuthUser.builder()
				.username(dto.getUsername())
				.password(password)
				.role(dto.getRole())
				.build();
		return authUserRepository.save(authUser);		
	}
	
	public TokenDto login(AuthUserDto dto) {
		Optional<AuthUser> user = authUserRepository.findByUsername(dto.getUsername());
		if(!user.isPresent()) {
			return null;
		}
		if(passwordEncoder.matches(dto.getPassword(), user.get().getPassword())) {
			return new TokenDto(jwtProvider.createToken(user.get()));
		}
		return null;
	}
	
	public TokenDto validate(String token, RequestDto dto) {
		if(!jwtProvider.validate(token, dto)) {
			return null;
		}
		String username = jwtProvider.getUserNameFromToken(token);
		if(!authUserRepository.findByUsername(username).isPresent()) {
			return null;
		}
		return new TokenDto(token);
	}
}
