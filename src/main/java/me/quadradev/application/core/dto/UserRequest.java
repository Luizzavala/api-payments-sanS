package me.quadradev.application.core.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import me.quadradev.application.core.model.Person;
import me.quadradev.application.core.model.User;
import me.quadradev.application.core.model.UserStatus;

public record UserRequest(
        @NotBlank @Email String email,
        @NotBlank String password,
        @NotBlank String firstName,
        String middleName,
        @NotBlank String lastName,
        String secondLastName,
        @NotNull UserStatus status
) {
    public User toEntity() {
        Person person = Person.builder()
                .firstName(firstName)
                .middleName(middleName)
                .lastName(lastName)
                .secondLastName(secondLastName)
                .build();
        return User.builder()
                .email(email)
                .password(password)
                .person(person)
                .status(status)
                .build();
    }
}
