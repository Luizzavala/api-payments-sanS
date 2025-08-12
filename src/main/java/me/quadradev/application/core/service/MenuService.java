package me.quadradev.application.core.service;

import lombok.RequiredArgsConstructor;
import me.quadradev.application.core.dto.MenuNodeDto;
import me.quadradev.application.core.model.*;
import me.quadradev.application.core.repository.MenuRepository;
import me.quadradev.application.core.repository.UserRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final UserRepository userRepository;
    private final MenuRepository menuRepository;

    @Transactional(readOnly = true)
    @Cacheable(value = "menuTree", key = "#email")
    public List<MenuNodeDto> getMenuTreeForUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Map<Menu, Integer> permissions = new HashMap<>();
        for (Role role : user.getRoles()) {
            for (RoleMenuPermission perm : role.getMenuPermissions()) {
                permissions.merge(perm.getMenu(), perm.getPermissions(), (a, b) -> a | b);
            }
        }
        // build tree starting from menus with no parent
        List<Menu> roots = permissions.keySet().stream()
                .filter(m -> m.getParent() == null)
                .sorted(Comparator.comparing(Menu::getId))
                .collect(Collectors.toList());
        return roots.stream().map(m -> buildNode(m, permissions)).collect(Collectors.toList());
    }

    private MenuNodeDto buildNode(Menu menu, Map<Menu, Integer> permissions) {
        Set<MenuAction> actions = Arrays.stream(MenuAction.values())
                .filter(a -> MenuAction.hasAction(permissions.get(menu), a))
                .collect(Collectors.toCollection(() -> EnumSet.noneOf(MenuAction.class)));
        List<MenuNodeDto> children = menu.getChildren().stream()
                .filter(permissions::containsKey)
                .sorted(Comparator.comparing(Menu::getId))
                .map(child -> buildNode(child, permissions))
                .collect(Collectors.toList());
        return new MenuNodeDto(menu.getId(), menu.getCode(), menu.getName(), actions, children);
    }
}
