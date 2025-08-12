package me.quadradev.application.core.service;

import lombok.RequiredArgsConstructor;
import me.quadradev.application.core.dto.RoleDto;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;
    private final RoleMenuPermissionRepository roleMenuPermissionRepository;
    private final RoleMapper roleMapper;
    private final UserMapper userMapper;

    @Transactional
    public RoleDto createRole(RoleRequest request) {
        roleRepository.findByName(request.name()).ifPresent(r -> {
            throw new ApiException("Role already exists", HttpStatus.CONFLICT);
        });
        Role role = roleMapper.toEntity(request);
        Role saved = roleRepository.save(role);
        return roleMapper.toDto(saved);
    }

    @Transactional
    public UserDto assignRolesToUser(Long userId, Set<String> roleNames) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException("User not found", HttpStatus.NOT_FOUND));

        Set<Role> roles = roleNames.stream()
                .map(name -> roleRepository.findByName(name)
                        .orElseThrow(() -> new ApiException("Role not found", HttpStatus.NOT_FOUND)))
                .collect(Collectors.toSet());

        user.getRoles().addAll(roles);
        User saved = userRepository.save(user);
        return userMapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public Page<UserDto> findUsersByRole(String roleName, Pageable pageable) {
        return userRepository.findUsersByRoleName(roleName, pageable)
                .map(userMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Set<RoleDto> getRolesByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException("User not found", HttpStatus.NOT_FOUND));
        return user.getRoles().stream()
                .map(roleMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Transactional
    @CacheEvict(value = "menuTree", allEntries = true)
    public void updateMenuPermissions(Long roleId, Long menuId, Set<MenuAction> actions) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ApiException("Role not found", HttpStatus.NOT_FOUND));
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new ApiException("Menu not found", HttpStatus.NOT_FOUND));

        RoleMenuPermission perm = roleMenuPermissionRepository.findByRoleAndMenu(role, menu)
                .orElse(RoleMenuPermission.builder().role(role).menu(menu).build());
        perm.setPermissions(MenuAction.toMask(actions));
        roleMenuPermissionRepository.save(perm);
    }
}
