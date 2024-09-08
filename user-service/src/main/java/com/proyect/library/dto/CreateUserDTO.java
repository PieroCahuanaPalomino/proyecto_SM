package com.proyect.library.dto;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CreateUserDTO {
	@Email
	@NotBlank
	private String email;

	@NotBlank
	private String username;

	@NotBlank
	private String password;
	private Set<String> roles;
}
