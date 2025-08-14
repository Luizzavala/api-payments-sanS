package me.quadradev.application.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthTokens> login(@RequestBody @Valid LoginRequest request) {
        AuthTokens tokens = authService.login(request.email(), request.password());
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokens.accessToken())
                .header("Refresh-Token", tokens.refreshToken())
                .body(tokens);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthTokens> refresh(@RequestHeader("Refresh-Token") String refreshToken) {
        AuthTokens tokens = authService.refreshAccessToken(refreshToken);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokens.accessToken())
                .header("Refresh-Token", tokens.refreshToken())
                .body(tokens);
    }
}
