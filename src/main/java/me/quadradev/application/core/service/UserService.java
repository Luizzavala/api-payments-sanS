package me.quadradev.application.core.service;

import lombok.RequiredArgsConstructor;
import me.quadradev.application.core.model.User;
import me.quadradev.application.core.model.UserStatus;
import me.quadradev.application.core.repository.UserRepository;
import me.quadradev.application.core.specification.UserSpecifications;
import me.quadradev.common.exception.ApiException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<User> searchUsers(String email, UserStatus status, String name) {
        Specification<User> spec = Specification.where(UserSpecifications.hasEmail(email))
                .and(UserSpecifications.hasStatus(status))
                .and(UserSpecifications.hasFirstName(name));
        return userRepository.findAll(spec);
    }

    public void deactivateUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ApiException("User not found", HttpStatus.NOT_FOUND));
        user.setStatus(UserStatus.INACTIVE);
        userRepository.save(user);
    }
}
