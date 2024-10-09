package com.proyect.library.model.mapper.user;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.proyect.library.model.dto.UserDto;
import com.proyect.library.model.entity.ERole;
import com.proyect.library.model.entity.RoleEntity;
import com.proyect.library.model.entity.UserEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(source = "username", target = "username")
    @Mapping(source = "email", target = "email")
    @Mapping(target = "roles", expression = "java(mapRolesFromEntity(entity.getRoles()))")
    UserDto entityToDto(UserEntity entity);

    @Mapping(source = "username", target = "username")
    @Mapping(source = "email", target = "email")
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", expression = "java(mapRolesFromDto(dto.getRoles()))")
    UserEntity dtoToEntity(UserDto dto);

    // Cambiado a mapRolesFromEntity para evitar conflicto
    default Set<String> mapRolesFromEntity(Set<RoleEntity> roles) {
        return roles.stream().map(role -> role.getName().name()).collect(Collectors.toSet());
    }

    // Cambiado a mapRolesFromDto para evitar conflicto
    default Set<RoleEntity> mapRolesFromDto(Set<String> roleNames) {
        return roleNames.stream()
                .map(name -> {
                    RoleEntity roleEntity = new RoleEntity();
                    roleEntity.setName(ERole.valueOf(name)); // Asegúrate de que ERole tenga los nombres correctos
                    return roleEntity;
                })
                .collect(Collectors.toSet());
    }
}

