package me.quadradev.application.core.dto;

import me.quadradev.application.core.model.MenuAction;

import java.util.Set;

public record RolePermissionRequest(Long menuId, Set<MenuAction> actions) {
}
