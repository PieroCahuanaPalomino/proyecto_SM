package com.proyect.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyect.library.dto.AuthUserDto;
import com.proyect.library.dto.NewUserDto;
import com.proyect.library.dto.RequestDto;
import com.proyect.library.dto.TokenDto;
import com.proyect.library.entity.AuthUser;
import com.proyect.library.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthUserController {
	@Autowired
	private AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<TokenDto> login(@RequestBody AuthUserDto dto){
		TokenDto tokenDto = authService.login(dto);
		if(tokenDto == null){
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(tokenDto);
	}
	
	@PostMapping("/validate")
	public ResponseEntity<TokenDto> validate(@RequestParam String token, @RequestBody RequestDto dto){
		TokenDto tokenDto = authService.validate(token, dto);
		if(tokenDto == null) {
			 return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(tokenDto);
	}
	
	@PostMapping("/create")
	public ResponseEntity<AuthUser> create(@RequestBody NewUserDto dto){
		AuthUser authUser = authService.save(dto);
		if(authUser == null) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(authUser);
	}
}
