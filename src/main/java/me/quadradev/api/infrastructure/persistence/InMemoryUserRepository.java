package me.quadradev.api.infrastructure.persistence;

import me.quadradev.api.application.ports.out.UserRepository;
import me.quadradev.api.domain.user.User;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Simple in-memory implementation of {@link UserRepository} for demonstration
 * purposes.
 */
@Repository
public class InMemoryUserRepository implements UserRepository {

    private final Map<Long, User> storage = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong();

    @Override
    public User save(User user) {
        Long id = user.id() != null ? user.id() : sequence.incrementAndGet();
        User persisted = new User(id, user.name(), user.email());
        storage.put(id, persisted);
        return persisted;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }
}
