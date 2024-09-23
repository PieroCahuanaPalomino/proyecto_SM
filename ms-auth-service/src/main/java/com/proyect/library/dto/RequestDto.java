package com.proyect.library.dto;

import java.util.List;

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
public class RequestDto {
    private String uri;
    private String method;
    private List<String> roles; // Lista de roles permitidos para esta ruta
}