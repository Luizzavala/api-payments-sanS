package me.quadradev.application.auth;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import me.quadradev.application.core.model.User;
import me.quadradev.application.core.model.UserStatus;
import me.quadradev.application.core.repository.UserRepository;
import me.quadradev.common.exception.ApiException;
import me.quadradev.common.security.JwtProvider;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public AuthTokens login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException("Credenciales inválidas", HttpStatus.UNAUTHORIZED));

        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new ApiException("Usuario inactivo", HttpStatus.FORBIDDEN);
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ApiException("Credenciales inválidas", HttpStatus.UNAUTHORIZED);
        }

        return new AuthTokens(
                jwtProvider.generateAccessToken(email),
                jwtProvider.generateRefreshToken(email)
        );
    }

    /**
     * Refreshes the authentication tokens using the provided refresh token.
     *
     * <p>The method not only validates the token signature/expiration but also
     * verifies that the associated user still exists and is active. This extra
     * validation prevents the reuse of refresh tokens from deleted or disabled
     * accounts, reinforcing the security of the refresh flow.</p>
     */
    public AuthTokens refreshAccessToken(String refreshToken) {
        try {
            // Validate token (signature + expiration) and extract the user identifier
            String email = jwtProvider.getEmailFromToken(refreshToken);

            // Look up the user and ensure it is active; otherwise reject the request
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new ApiException("Usuario no encontrado", HttpStatus.UNAUTHORIZED));

            if (user.getStatus() != UserStatus.ACTIVE) {
                throw new ApiException("Usuario inactivo", HttpStatus.FORBIDDEN);
            }

            // Generate and return new token pair
            return new AuthTokens(
                    jwtProvider.generateAccessToken(email),
                    jwtProvider.generateRefreshToken(email)
            );

        } catch (ExpiredJwtException e) {
            throw new ApiException("Refresh token expirado", HttpStatus.UNAUTHORIZED);
        } catch (JwtException | IllegalArgumentException e) {
            throw new ApiException("Refresh token inválido", HttpStatus.UNAUTHORIZED);
        }
    }
}
