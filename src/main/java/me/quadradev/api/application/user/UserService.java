package me.quadradev.api.application.user;

import me.quadradev.api.application.ports.in.CreateUserUseCase;
import me.quadradev.api.application.ports.in.GetUserUseCase;
import me.quadradev.api.application.ports.out.UserRepository;
import me.quadradev.api.domain.user.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of user-related use cases.
 */
@Service
public class UserService implements CreateUserUseCase, GetUserUseCase {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(String name, String email) {
        User user = new User(null, name, email);
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }
}
