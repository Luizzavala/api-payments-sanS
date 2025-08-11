package me.quadradev.application.core.dto;

import jakarta.validation.constraints.NotBlank;

public record RoleRequest(@NotBlank String name) {
}
