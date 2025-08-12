package me.quadradev.application.core.repository;

import me.quadradev.application.core.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
