package me.quadradev.application.core.controller;

import lombok.RequiredArgsConstructor;
import me.quadradev.application.core.model.Role;
import me.quadradev.application.core.model.User;
import me.quadradev.application.core.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<Role> create(@RequestBody Role role) {
        Role created = roleService.createRole(role);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping("/{userId}/assign")
    public ResponseEntity<User> assignRoles(@PathVariable Long userId, @RequestBody Set<String> roles) {
        User user = roleService.assignRolesToUser(userId, roles);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{roleName}/users")
    public List<User> getUsersByRole(@PathVariable String roleName) {
        return roleService.findUsersByRole(roleName);
    }

    @GetMapping("/user/{userId}")
    public Set<Role> getRolesByUser(@PathVariable Long userId) {
        return roleService.getRolesByUser(userId);
    }
}
