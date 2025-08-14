package me.quadradev.application.core.repository;

import me.quadradev.application.core.model.User;
import me.quadradev.application.core.model.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    List<User> findByStatus(UserStatus status);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = :roleName")
    Page<User> findUsersByRoleName(@Param("roleName") String roleName, Pageable pageable);
}
