package me.quadradev.api.adapters.incoming.rest.dto;

/**
 * DTO representing the payload required to create a new user.
 */
public record CreateUserRequest(String name, String email) {
}
