package me.quadradev.common.security;

import io.jsonwebtoken.Claims;
import me.quadradev.application.core.model.Role;
import me.quadradev.application.core.model.User;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class JwtProviderTest {

    private JwtProvider buildProvider() {
        JwtProperties properties = new JwtProperties();
        properties.setSecret("0123456789ABCDEF0123456789ABCDEF");
        properties.setAccessTokenExpirationMs(3600000); // 1 hour
        properties.setRefreshTokenExpirationMs(7200000); // 2 hours
        return new JwtProvider(properties);
    }

    private User buildUser() {
        return User.builder()
                .id(1L)
                .email("user@example.com")
                .roles(Set.of(Role.builder().name("ADMIN").build()))
                .build();
    }

    @Test
    void generateTokenAndValidateClaims() {
        JwtProvider provider = buildProvider();
        User user = buildUser();

        String token = provider.generateAccessToken(user);
        assertTrue(provider.validateToken(token));
        assertEquals(user.getEmail(), provider.getEmailFromToken(token));

        Claims claims = provider.getClaims(token);
        assertEquals(1L, ((Number) claims.get("id")).longValue());
        assertEquals(user.getEmail(), claims.get("email"));
        assertTrue(((java.util.List<?>) claims.get("roles")).contains("ADMIN"));
    }
}
