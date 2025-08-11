package me.quadradev.application.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import me.quadradev.application.core.model.UserStatus;

public record UserRequest(
        @NotBlank @Email String email,
        @NotBlank @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) String password,
        @NotBlank String firstName,
        String middleName,
        @NotBlank String lastName,
        String secondLastName,
        @NotNull UserStatus status
) {
}
