package com.proyect.library.security;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.proyect.library.dto.RequestDto;
import com.proyect.library.entity.ERole;

import lombok.Getter;
import lombok.Setter;

@Component
@Setter
@Getter
@ConfigurationProperties(prefix = "admin-paths")
public class RouteValidator {
    private List<RequestDto> paths;

    public boolean isAuthorized(String method, String uri, Set<ERole> userRoles) {
        for (RequestDto path : paths) {
            if (Pattern.matches(path.getUri(), uri) && path.getMethod().equalsIgnoreCase(method)) {
                // Verificar si el usuario tiene al menos uno de los roles necesarios
                return path.getRoles().stream()
                           .map(ERole::valueOf)
                           .anyMatch(userRoles::contains);
            }
        }
        return false; // No autorizado si no se encuentra coincidencia
    }

}







