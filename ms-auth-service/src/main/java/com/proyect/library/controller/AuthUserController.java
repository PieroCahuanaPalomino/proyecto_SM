package com.proyect.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;

import com.proyect.library.model.dto.AuthUserDto;
import com.proyect.library.model.dto.RequestDto;
import com.proyect.library.model.dto.TokenDto;
import com.proyect.library.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthUserController {
	@Autowired
	private AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<TokenDto> login(@RequestBody AuthUserDto dto){
		TokenDto tokenDto = authService.login(dto);
		return ResponseEntity.ok(tokenDto);
	}
	
	@PostMapping("/validate")
    public ResponseEntity<TokenDto> validate(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, 
            @RequestBody RequestDto dto) {
        
        // Extraer el token de la cabecera
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7); // Quitar "Bearer "
            TokenDto tokenDto = authService.validate(token, dto);
            return ResponseEntity.ok(tokenDto);
        }
        
        throw new IllegalArgumentException("Authorization header is invalid");
    }
	
	@GetMapping("/secured")
	public ResponseEntity<String> secured() {
     
            return ResponseEntity.ok("ACCESO");
        
    }
}
