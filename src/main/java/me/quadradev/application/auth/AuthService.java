package me.quadradev.application.auth;

import lombok.RequiredArgsConstructor;
import me.quadradev.application.core.model.User;
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

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ApiException("Credenciales inválidas", HttpStatus.UNAUTHORIZED);
        }

        return new AuthTokens(
                jwtProvider.generateAccessToken(email),
                jwtProvider.generateRefreshToken(email)
        );
    }

    public String refreshAccessToken(String refreshToken) {
        if (!jwtProvider.validateToken(refreshToken)) {
            throw new ApiException("Refresh token inválido o expirado", HttpStatus.UNAUTHORIZED);
        }
        String email = jwtProvider.getEmailFromToken(refreshToken);
        return jwtProvider.generateAccessToken(email);
    }
}
