package com.proyect.library.security;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.proyect.library.model.dto.RequestDto;
import com.proyect.library.model.dto.UserDto;
import com.proyect.library.model.entity.UserEntity;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;

@Component
public class JwtProvider {
	
	
	@Value("${jwt.secret}")
	private String secret;
	
	@PostConstruct
	protected void init() {
		secret = Base64.getEncoder().encodeToString(secret.getBytes());
	}
	
	public String createToken(UserDto  user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", user.getUsername());
        claims.put("roles", user.getRoles());

        Date now = new Date();
        Date exp = new Date(now.getTime() + 3600000); // 1 hora de expiración
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }
	
	public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

	 public String getUserNameFromToken(String token) {
	        try {
	            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().get("username", String.class);
	        } catch (Exception e) {
	            return null; // Manejo de errores
	        }
	}
}
