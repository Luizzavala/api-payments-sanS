# Seguridad

La aplicación utiliza **Spring Security** con autenticación basada en **JWT** para proteger los endpoints.

## Componentes principales
- `SecurityConfig` define la cadena de filtros, deshabilita CSRF, establece sesiones *stateless* y configura los puntos públicos.
- `JwtFilter` intercepta cada petición y valida el token presente en el encabezado `Authorization`.
- `JwtProvider` genera y verifica los tokens de acceso y de refresco.
- `AuthService` gestiona el proceso de inicio de sesión y la renovación de tokens.
- Las contraseñas de los usuarios se almacenan mediante `BCryptPasswordEncoder`.

## Flujo de autenticación
1. El usuario envía sus credenciales a `/api/auth/login`.
2. `AuthService` verifica la información y retorna un par de tokens.
3. El cliente envía el token de acceso en el encabezado `Authorization: Bearer <token>`.
4. `JwtFilter` valida el token y, si es correcto, establece la autenticación en el contexto de seguridad.
