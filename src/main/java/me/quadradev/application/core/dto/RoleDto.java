package me.quadradev.application.core.dto;

import me.quadradev.application.core.model.Role;

public record RoleDto(Long id, String name) {
    public static RoleDto fromEntity(Role role) {
        return new RoleDto(role.getId(), role.getName());
    }
}
