package me.quadradev.application.core.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import me.quadradev.application.core.dto.RoleDto;
import me.quadradev.application.core.dto.RoleRequest;
import me.quadradev.application.core.dto.UserDto;
import me.quadradev.application.core.service.RoleService;
import me.quadradev.common.util.PageResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@Validated
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<RoleDto> create(@RequestBody @Valid RoleRequest request) {
        RoleDto created = roleService.createRole(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping("/{userId}/assign")
    public ResponseEntity<UserDto> assignRoles(@PathVariable Long userId, @RequestBody Set<@NotBlank String> roles) {
        UserDto user = roleService.assignRolesToUser(userId, roles);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{roleName}/users")
    public PageResponse<UserDto> getUsersByRole(@PathVariable String roleName, Pageable pageable) {
        return PageResponse.of(roleService.findUsersByRole(roleName, pageable));
    }

    @GetMapping("/user/{userId}")
    public Set<RoleDto> getRolesByUser(@PathVariable Long userId) {
        return roleService.getRolesByUser(userId);
    }
}
