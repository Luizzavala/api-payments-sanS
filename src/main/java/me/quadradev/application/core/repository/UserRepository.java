package me.quadradev.application.core.repository;

import me.quadradev.application.core.model.User;
import me.quadradev.application.core.model.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByStatus(UserStatus status);
}
