package com.proyect.library.security;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.proyect.library.model.dto.RequestDto;
import com.proyect.library.model.entity.ERole;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "admin-paths")
public class RouteValidator {
    private List<RequestDto> paths;
    private RouteNode root;

    @PostConstruct
    public void init() {
        root = new RouteNode();
        for (RequestDto path : paths) {
            addPathToTree(root, path.getUri(), path.getMethod(), path.getRoles());
        }
    }

    private void addPathToTree(RouteNode node, String uri, String method, List<String> roles) {
        String[] segments = uri.split("/");
        RouteNode currentNode = node;

        for (String segment : segments) {
            currentNode = currentNode.getChildren().computeIfAbsent(segment, k -> new RouteNode());
        }

        currentNode.getRoles().add(method); // Almacenar el método en el nodo
        currentNode.getRoles().addAll(roles); // Almacenar los roles permitidos
    }

    public boolean isAuthorized(String method, String uri, Set<ERole> userRoles) {
        String[] segments = uri.split("/");
        return isAuthorizedRecursive(root, segments, method, 0, userRoles);
    }

    private boolean isAuthorizedRecursive(RouteNode node, String[] segments, String method, int index, Set<ERole> userRoles) {
        if (index == segments.length) {
            // Verificar si el usuario tiene alguno de los roles necesarios
            return userRoles.stream().anyMatch(role -> node.getRoles().contains(role.name()));
        }

        String segment = segments[index];
        RouteNode childNode = node.getChildren().get(segment);
        if (childNode != null) {
            if (isAuthorizedRecursive(childNode, segments, method, index + 1, userRoles)) {
                return true;
            }
        }

        return false; // No autorizado si no se encuentra coincidencia
    }
}





