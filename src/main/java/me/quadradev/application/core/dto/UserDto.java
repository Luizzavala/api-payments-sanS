package me.quadradev.application.core.dto;

import me.quadradev.application.core.model.UserStatus;

import java.util.Set;

public record UserDto(
        Long id,
        String email,
        String firstName,
        String middleName,
        String lastName,
        String secondLastName,
        UserStatus status,
        Set<String> roles
) {
}
