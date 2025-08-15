package me.quadradev.application.core.service;

import me.quadradev.application.core.dto.MenuNodeDto;
import me.quadradev.application.core.model.*;
import me.quadradev.application.core.repository.MenuRepository;
import me.quadradev.application.core.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MenuServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private MenuRepository menuRepository;

    @InjectMocks
    private MenuService menuService;

    @Test
    void getMenuTreeForUserBuildsTree() {
        Menu child = Menu.builder().id(2L).code("child").name("Child").build();
        Menu root = Menu.builder().id(1L).code("root").name("Root").children(Set.of(child)).build();
        child.setParent(root);

        RoleMenuPermission permRoot = RoleMenuPermission.builder()
                .menu(root)
                .permissions(MenuAction.toMask(Set.of(MenuAction.VIEW, MenuAction.CREATE)))
                .build();
        RoleMenuPermission permChild = RoleMenuPermission.builder()
                .menu(child)
                .permissions(MenuAction.toMask(Set.of(MenuAction.VIEW)))
                .build();

        Role role = Role.builder().menuPermissions(Set.of(permRoot, permChild)).build();
        User user = User.builder().email("user@example.com").roles(Set.of(role)).build();
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));

        List<MenuNodeDto> tree = menuService.getMenuTreeForUser("user@example.com");
        assertEquals(1, tree.size());
        MenuNodeDto rootNode = tree.get(0);
        assertEquals(1L, rootNode.id());
        assertTrue(rootNode.actions().contains(MenuAction.VIEW));
        assertTrue(rootNode.actions().contains(MenuAction.CREATE));
        assertEquals(1, rootNode.children().size());
        assertEquals(2L, rootNode.children().get(0).id());
    }

    @Test
    void getMenuTreeForUserUserNotFoundThrows() {
        when(userRepository.findByEmail("missing@example.com")).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> menuService.getMenuTreeForUser("missing@example.com"));
    }
}

