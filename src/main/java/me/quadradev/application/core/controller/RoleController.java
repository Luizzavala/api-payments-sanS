package me.quadradev.application.core.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import me.quadradev.application.core.dto.RoleDto;
import me.quadradev.application.core.dto.RoleRequest;
import me.quadradev.application.core.dto.UserDto;
import me.quadradev.application.core.model.Role;
import me.quadradev.application.core.model.User;
import me.quadradev.application.core.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@Validated
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<RoleDto> create(@RequestBody @Valid RoleRequest request) {
        Role created = roleService.createRole(request.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(RoleDto.fromEntity(created));
    }

    @PostMapping("/{userId}/assign")
    public ResponseEntity<UserDto> assignRoles(@PathVariable Long userId, @RequestBody Set<@NotBlank String> roles) {
        User user = roleService.assignRolesToUser(userId, roles);
        return ResponseEntity.ok(UserDto.fromEntity(user));
    }

    @GetMapping("/{roleName}/users")
    public List<UserDto> getUsersByRole(@PathVariable String roleName) {
        return roleService.findUsersByRole(roleName).stream()
                .map(UserDto::fromEntity).toList();
    }

    @GetMapping("/user/{userId}")
    public Set<RoleDto> getRolesByUser(@PathVariable Long userId) {
        return roleService.getRolesByUser(userId).stream()
                .map(RoleDto::fromEntity).collect(Collectors.toSet());
    }
}
