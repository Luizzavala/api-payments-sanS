package me.quadradev.api.application.ports.in;

import me.quadradev.api.domain.user.User;

/**
 * Use case for creating a new user.
 */
public interface CreateUserUseCase {
    User createUser(String name, String email);
}
