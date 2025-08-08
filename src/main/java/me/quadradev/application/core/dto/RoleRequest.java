package me.quadradev.application.core.dto;

import me.quadradev.application.core.model.Role;

public record RoleRequest(String name) {
    public Role toEntity() {
        return Role.builder().name(name).build();
    }
}
