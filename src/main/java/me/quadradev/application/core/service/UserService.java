package me.quadradev.application.core.service;

import lombok.RequiredArgsConstructor;
import me.quadradev.application.core.model.User;
import me.quadradev.application.core.model.Person;
import me.quadradev.application.core.model.UserStatus;
import me.quadradev.application.core.repository.UserRepository;
import me.quadradev.application.core.specification.UserSpecifications;
import me.quadradev.common.exception.ApiException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    public User createUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new ApiException("Email already exists", HttpStatus.CONFLICT);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<User> searchUsers(String email, UserStatus status, String name) {
        Specification<User> spec = Specification.where(UserSpecifications.hasEmail(email))
                .and(UserSpecifications.hasStatus(status))
                .and(UserSpecifications.hasFirstName(name));
        return userRepository.findAll(spec);
    }

    @Transactional
    public void deactivateUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ApiException("User not found", HttpStatus.NOT_FOUND));
        user.setStatus(UserStatus.INACTIVE);
        userRepository.save(user);
    }

    @Transactional
    public User updateUser(Long id, User updatedUser) {
        Specification<User> spec = Specification.where(UserSpecifications.hasId(id));
        User existingUser = userRepository.findOne(spec)
                .orElseThrow(() -> new ApiException("User not found", HttpStatus.NOT_FOUND));

        if (updatedUser.getEmail() != null && !updatedUser.getEmail().equals(existingUser.getEmail())) {
            Specification<User> emailSpec = Specification.where(UserSpecifications.hasEmail(updatedUser.getEmail()));
            userRepository.findOne(emailSpec).ifPresent(u -> {
                throw new ApiException("Email already exists", HttpStatus.CONFLICT);
            });
            existingUser.setEmail(updatedUser.getEmail());
        }

        if (updatedUser.getPerson() != null) {
            Person existingPerson = existingUser.getPerson();
            if (existingPerson == null) {
                existingPerson = new Person();
            }
            Person updatedPerson = updatedUser.getPerson();
            if (updatedPerson.getFirstName() != null) {
                existingPerson.setFirstName(updatedPerson.getFirstName());
            }
            if (updatedPerson.getMiddleName() != null) {
                existingPerson.setMiddleName(updatedPerson.getMiddleName());
            }
            if (updatedPerson.getLastName() != null) {
                existingPerson.setLastName(updatedPerson.getLastName());
            }
            if (updatedPerson.getSecondLastName() != null) {
                existingPerson.setSecondLastName(updatedPerson.getSecondLastName());
            }
            existingUser.setPerson(existingPerson);
        }

        if (updatedUser.getStatus() != null) {
            existingUser.setStatus(updatedUser.getStatus());
        }

        return userRepository.save(existingUser);
    }

    @Transactional
    public void changePassword(Long id, String currentPassword, String newPassword) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ApiException("User not found", HttpStatus.NOT_FOUND));
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new ApiException("Current password is incorrect", HttpStatus.UNAUTHORIZED);
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}
