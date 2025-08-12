package me.quadradev.application.core.mapper;

import me.quadradev.application.core.dto.UserDto;
import me.quadradev.application.core.dto.UserRequest;
import me.quadradev.application.core.model.Role;
import me.quadradev.application.core.model.User;
import org.mapstruct.*;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(config = MapStructConfig.class)
public interface UserMapper {

    @Mapping(target = "firstName", source = "person.firstName")
    @Mapping(target = "middleName", source = "person.middleName")
    @Mapping(target = "lastName", source = "person.lastName")
    @Mapping(target = "secondLastName", source = "person.secondLastName")
    @Mapping(target = "roles", source = "roles", qualifiedByName = "rolesToNames")
    UserDto toDto(User user);

    @Mapping(target = "person.firstName", source = "firstName")
    @Mapping(target = "person.middleName", source = "middleName")
    @Mapping(target = "person.lastName", source = "lastName")
    @Mapping(target = "person.secondLastName", source = "secondLastName")
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User toEntity(UserRequest request);

    @Mapping(target = "person.firstName", source = "firstName")
    @Mapping(target = "person.middleName", source = "middleName")
    @Mapping(target = "person.lastName", source = "lastName")
    @Mapping(target = "person.secondLastName", source = "secondLastName")
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateUserFromRequest(UserRequest request, @MappingTarget User user);

    @Named("rolesToNames")
    default Set<String> rolesToNames(Set<Role> roles) {
        return roles.stream().map(Role::getName).collect(Collectors.toSet());
    }
}
