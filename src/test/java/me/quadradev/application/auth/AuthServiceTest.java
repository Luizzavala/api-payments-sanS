package me.quadradev.application.auth;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import me.quadradev.application.core.model.Role;
import me.quadradev.application.core.model.User;
import me.quadradev.application.core.model.UserStatus;
import me.quadradev.application.core.repository.UserRepository;
import me.quadradev.common.exception.ApiException;
import me.quadradev.common.security.JwtProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @Test
    void loginReturnsTokensWhenCredentialsAreValid() {
        User user = User.builder()
                .id(1L)
                .email("user@example.com")
                .password("encoded")
                .status(UserStatus.ACTIVE)
                .roles(Set.of(Role.builder().name("ADMIN").build()))
                .build();

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("secret", "encoded")).thenReturn(true);
        when(jwtProvider.generateToken(anyMap(), eq(user.getEmail()), anyLong()))
                .thenReturn("access").thenReturn("refresh");

        AuthTokens tokens = authService.login("user@example.com", "secret");

        assertEquals("access", tokens.accessToken());
        assertEquals("refresh", tokens.refreshToken());
    }

    @Test
    void loginThrowsApiExceptionWhenUserNotFound() {
        when(userRepository.findByEmail("missing@example.com")).thenReturn(Optional.empty());

        ApiException ex = assertThrows(ApiException.class, () -> authService.login("missing@example.com", "pwd"));
        assertEquals("Credenciales inválidas", ex.getMessage());
        assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatus());
    }

    @Test
    void loginThrowsApiExceptionWhenUserInactive() {
        User inactive = User.builder()
                .email("user@example.com")
                .password("encoded")
                .status(UserStatus.INACTIVE)
                .build();

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(inactive));

        ApiException ex = assertThrows(ApiException.class, () -> authService.login("user@example.com", "secret"));
        assertEquals("Usuario inactivo", ex.getMessage());
        assertEquals(HttpStatus.FORBIDDEN, ex.getStatus());
    }

    @Test
    void loginThrowsApiExceptionWhenPasswordInvalid() {
        User user = User.builder()
                .email("user@example.com")
                .password("encoded")
                .status(UserStatus.ACTIVE)
                .build();

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong", "encoded")).thenReturn(false);

        ApiException ex = assertThrows(ApiException.class, () -> authService.login("user@example.com", "wrong"));
        assertEquals("Credenciales inválidas", ex.getMessage());
        assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatus());
    }

    @Test
    void refreshAccessTokenExpiredThrowsApiException() {
        when(jwtProvider.getEmailFromToken("bad")).thenThrow(new ExpiredJwtException(null, null, "exp"));

        ApiException ex = assertThrows(ApiException.class, () -> authService.refreshAccessToken("bad"));
        assertEquals("Refresh token expirado", ex.getMessage());
        assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatus());
    }

    @Test
    void refreshAccessTokenUserNotFoundThrowsApiException() {
        when(jwtProvider.getEmailFromToken("token")).thenReturn("user@example.com");
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.empty());

        ApiException ex = assertThrows(ApiException.class, () -> authService.refreshAccessToken("token"));
        assertEquals("Usuario no encontrado", ex.getMessage());
        assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatus());
    }

    @Test
    void refreshAccessTokenInvalidTokenThrowsApiException() {
        when(jwtProvider.getEmailFromToken("bad")).thenThrow(new JwtException("bad"));

        ApiException ex = assertThrows(ApiException.class, () -> authService.refreshAccessToken("bad"));
        assertEquals("Refresh token inválido", ex.getMessage());
        assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatus());
    }
}

