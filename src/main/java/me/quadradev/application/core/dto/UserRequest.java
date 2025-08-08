package me.quadradev.application.core.dto;

import me.quadradev.application.core.model.Person;
import me.quadradev.application.core.model.User;
import me.quadradev.application.core.model.UserStatus;

public record UserRequest(
        String email,
        String password,
        String firstName,
        String middleName,
        String lastName,
        String secondLastName,
        UserStatus status
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
