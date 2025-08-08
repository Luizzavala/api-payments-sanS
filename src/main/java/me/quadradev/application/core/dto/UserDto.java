package me.quadradev.application.core.dto;

import me.quadradev.application.core.model.Person;
import me.quadradev.application.core.model.Role;
import me.quadradev.application.core.model.User;
import me.quadradev.application.core.model.UserStatus;

import java.util.Set;
import java.util.stream.Collectors;

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
    public static UserDto fromEntity(User user) {
        Person person = user.getPerson();
        return new UserDto(
                user.getId(),
                user.getEmail(),
                person != null ? person.getFirstName() : null,
                person != null ? person.getMiddleName() : null,
                person != null ? person.getLastName() : null,
                person != null ? person.getSecondLastName() : null,
                user.getStatus(),
                user.getRoles().stream().map(Role::getName).collect(Collectors.toSet())
        );
    }
}
