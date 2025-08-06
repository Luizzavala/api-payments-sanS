package me.quadradev.api.application.ports.out;

import me.quadradev.api.domain.user.User;

import java.util.Optional;

/**
 * Port for persisting and retrieving users.
 */
public interface UserRepository {
    User save(User user);
    Optional<User> findById(Long id);
}
