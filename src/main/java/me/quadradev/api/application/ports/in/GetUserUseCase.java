package me.quadradev.api.application.ports.in;

import me.quadradev.api.domain.user.User;

import java.util.Optional;

/**
 * Use case for retrieving a user by its identifier.
 */
public interface GetUserUseCase {
    Optional<User> getUser(Long id);
}
