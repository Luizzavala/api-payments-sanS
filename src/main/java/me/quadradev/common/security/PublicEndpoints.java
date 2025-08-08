package me.quadradev.common.security;

public final class PublicEndpoints {
    private PublicEndpoints() {}

    public static final String[] DEV = {
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/api/auth/**",
            "/api/users/**",
            "/api/roles/**"
    };

    public static final String[] PROD = {
            "/api/auth/**"
    };
}
