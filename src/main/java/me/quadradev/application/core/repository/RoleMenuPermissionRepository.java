package me.quadradev.application.core.repository;

import me.quadradev.application.core.model.Menu;
import me.quadradev.application.core.model.Role;
import me.quadradev.application.core.model.RoleMenuPermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleMenuPermissionRepository extends JpaRepository<RoleMenuPermission, Long> {
    Optional<RoleMenuPermission> findByRoleAndMenu(Role role, Menu menu);
}
