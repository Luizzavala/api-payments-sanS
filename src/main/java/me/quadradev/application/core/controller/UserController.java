package me.quadradev.application.core.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.quadradev.application.core.dto.ChangePasswordRequest;
import me.quadradev.application.core.dto.UserDto;
import me.quadradev.application.core.dto.UserRequest;
import me.quadradev.application.core.model.UserStatus;
import me.quadradev.application.core.service.UserService;
import me.quadradev.common.util.ApiResponse;
import me.quadradev.common.util.PageResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    @GetMapping
    public PageResponse<UserDto> findAll(Pageable pageable) {
        return PageResponse.of(userService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody @Valid UserRequest request) {
        UserDto created = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable Long id, @RequestBody @Valid UserRequest request) {
        UserDto updated = userService.updateUser(id, request);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<Void> changePassword(@PathVariable Long id, @RequestBody @Valid ChangePasswordRequest request) {
        userService.changePassword(id, request.currentPassword(), request.newPassword());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<PageResponse<UserDto>>> searchUsers(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) UserStatus status,
            @RequestParam(required = false) String name,
            Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.ok("Users retrieved",
                PageResponse.of(userService.searchUsers(email, status, name, pageable))));
    }
}
