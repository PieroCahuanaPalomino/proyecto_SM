package com.proyect.library.security;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.proyect.library.dto.RequestDto;

import lombok.Getter;
import lombok.Setter;

@Component
@Setter
@Getter
@ConfigurationProperties(prefix = "admin-paths")
public class RouteValidator {

	private List<RequestDto> paths;

	public boolean isAdminPath(RequestDto dto) {
       return paths.stream().anyMatch(p ->
       Pattern.matches(p.getUri(), dto.getUri()) && p.getMethod().equals(dto.getMethod()));
	}
}
