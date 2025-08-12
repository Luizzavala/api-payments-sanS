package me.quadradev.application.core.controller;

import lombok.RequiredArgsConstructor;
import me.quadradev.application.core.dto.MenuNodeDto;
import me.quadradev.application.core.service.MenuService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/tree")
    public List<MenuNodeDto> getMenuTree(Authentication authentication) {
        String email = (String) authentication.getPrincipal();
        return menuService.getMenuTreeForUser(email);
    }
}
