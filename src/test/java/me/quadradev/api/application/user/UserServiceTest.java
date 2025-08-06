package me.quadradev.api.application.user;

import me.quadradev.api.application.ports.out.UserRepository;
import me.quadradev.api.domain.user.User;
import me.quadradev.api.infrastructure.persistence.InMemoryUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp() {
        UserRepository repository = new InMemoryUserRepository();
        userService = new UserService(repository);
    }

    @Test
    void createAndRetrieveUser() {
        User created = userService.createUser("Alice", "alice@example.com");
        assertNotNull(created.id());

        Optional<User> retrieved = userService.getUser(created.id());
        assertTrue(retrieved.isPresent());
        assertEquals("Alice", retrieved.get().name());
        assertEquals("alice@example.com", retrieved.get().email());
    }
}
