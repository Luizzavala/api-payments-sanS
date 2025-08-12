package me.quadradev.application.core.dto;

import me.quadradev.application.core.model.MenuAction;

import java.util.List;
import java.util.Set;

public record MenuNodeDto(Long id, String code, String name, Set<MenuAction> actions, List<MenuNodeDto> children) {
}
