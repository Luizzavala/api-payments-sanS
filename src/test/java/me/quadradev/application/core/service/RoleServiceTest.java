package me.quadradev.application.core.service;

import me.quadradev.application.core.dto.RoleRequest;
import me.quadradev.application.core.dto.UserDto;
import me.quadradev.application.core.mapper.RoleMapper;
import me.quadradev.application.core.mapper.UserMapper;
import me.quadradev.application.core.model.*;
import me.quadradev.application.core.repository.MenuRepository;
import me.quadradev.application.core.repository.RoleMenuPermissionRepository;
import me.quadradev.application.core.repository.RoleRepository;
import me.quadradev.application.core.repository.UserRepository;
import me.quadradev.common.exception.ApiException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private MenuRepository menuRepository;
    @Mock
    private RoleMenuPermissionRepository roleMenuPermissionRepository;
    @Mock
    private RoleMapper roleMapper;
    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private RoleService roleService;

    @Test
    void createRoleThrowsWhenNameExists() {
        RoleRequest request = new RoleRequest("ADMIN");
        when(roleRepository.findByName("ADMIN")).thenReturn(Optional.of(Role.builder().build()));

        ApiException ex = assertThrows(ApiException.class, () -> roleService.createRole(request));
        assertEquals("Role already exists", ex.getMessage());
        assertEquals(HttpStatus.CONFLICT, ex.getStatus());
    }

    @Test
    void assignRolesToUserAddsRoles() {
        User user = User.builder().id(1L).roles(new java.util.HashSet<>()).build();
        Role role = Role.builder().name("ADMIN").build();
        UserDto dto = new UserDto(1L, null, null, null, null, null, UserStatus.ACTIVE, Set.of("ADMIN"));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(roleRepository.findByName("ADMIN")).thenReturn(Optional.of(role));
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(dto);

        UserDto result = roleService.assignRolesToUser(1L, Set.of("ADMIN"));
        assertEquals(dto, result);
        assertTrue(user.getRoles().contains(role));
    }

    @Test
    void updateMenuPermissionsSavesMask() {
        Role role = Role.builder().id(1L).build();
        Menu menu = Menu.builder().id(2L).build();

        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(menuRepository.findById(2L)).thenReturn(Optional.of(menu));
        when(roleMenuPermissionRepository.findByRoleAndMenu(role, menu)).thenReturn(Optional.empty());

        roleService.updateMenuPermissions(1L, 2L, Set.of(MenuAction.VIEW, MenuAction.CREATE));

        ArgumentCaptor<RoleMenuPermission> captor = ArgumentCaptor.forClass(RoleMenuPermission.class);
        verify(roleMenuPermissionRepository).save(captor.capture());
        RoleMenuPermission saved = captor.getValue();
        assertEquals(MenuAction.toMask(Set.of(MenuAction.VIEW, MenuAction.CREATE)), saved.getPermissions());
        assertEquals(role, saved.getRole());
        assertEquals(menu, saved.getMenu());
    }
}

