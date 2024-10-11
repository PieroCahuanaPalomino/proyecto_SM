package com.proyect.library.security;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RouteNode {
    private Map<String, RouteNode> children = new HashMap<>();
    private Set<String> roles = new HashSet<>();
}