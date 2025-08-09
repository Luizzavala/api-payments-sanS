package me.quadradev.application.core.dto;

import jakarta.validation.constraints.NotBlank;
import me.quadradev.application.core.model.Role;

public record RoleRequest(@NotBlank String name) {
    public Role toEntity() {
        return Role.builder().name(name).build();
    }
}
