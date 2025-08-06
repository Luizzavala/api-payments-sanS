package me.quadradev.api.domain.user;

/**
 * Domain entity representing a user of the system.
 */
public record User(Long id, String name, String email) {
}
