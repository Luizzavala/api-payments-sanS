package me.quadradev.application.core.controller;

import lombok.RequiredArgsConstructor;
import me.quadradev.application.core.dto.UserDto;
import me.quadradev.application.core.dto.UserRequest;
import me.quadradev.application.core.model.User;
import me.quadradev.application.core.model.UserStatus;
import me.quadradev.application.core.service.UserService;
import me.quadradev.common.util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDto> findAll() {
        return userService.findAll().stream().map(UserDto::fromEntity).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {
        return userService.findById(id)
                .map(user -> ResponseEntity.ok(UserDto.fromEntity(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody UserRequest request) {
        User created = userService.createUser(request.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(UserDto.fromEntity(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable Long id, @RequestBody UserRequest request) {
        User updated = userService.updateUser(id, request.toEntity());
        return ResponseEntity.ok(UserDto.fromEntity(updated));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<UserDto>>> searchUsers(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) UserStatus status,
            @RequestParam(required = false) String name) {
        List<UserDto> users = userService.searchUsers(email, status, name).stream()
                .map(UserDto::fromEntity).toList();
        return ResponseEntity.ok(ApiResponse.ok("Users retrieved", users));
    }
}
