package me.quadradev.application.core.service;

import lombok.RequiredArgsConstructor;
import me.quadradev.application.core.model.Role;
import me.quadradev.application.core.model.User;
import me.quadradev.application.core.repository.RoleRepository;
import me.quadradev.application.core.repository.UserRepository;
import me.quadradev.common.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public Role createRole(Role role) {
        roleRepository.findByName(role.getName()).ifPresent(r -> {
            throw new ApiException("Role already exists", HttpStatus.CONFLICT);
        });
        return roleRepository.save(role);
    }

    public User assignRolesToUser(Long userId, Set<String> roleNames) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException("User not found", HttpStatus.NOT_FOUND));

        Set<Role> roles = roleNames.stream()
                .map(name -> roleRepository.findByName(name)
                        .orElseThrow(() -> new ApiException("Role not found", HttpStatus.NOT_FOUND)))
                .collect(Collectors.toSet());

        user.getRoles().addAll(roles);
        return userRepository.save(user);
    }

    public List<User> findUsersByRole(String roleName) {
        return userRepository.findUsersByRoleName(roleName);
    }

    public Set<Role> getRolesByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException("User not found", HttpStatus.NOT_FOUND));
        return user.getRoles();
    }
}
