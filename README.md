# API Payments

[Arquitectura](docs/architecture.md) | [Seguridad](docs/security.md)

## Configuración del entorno

Copia el archivo `.env.example` a `.env` y completa los valores reales antes de ejecutar la aplicación:

```bash
cp .env.example .env
# Edita .env con tus credenciales
```

El archivo `application.properties` toma los datos de conexión a la base de datos
desde las variables definidas en `.env` (`DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USERNAME`
y `DB_PASSWORD`).

## Licencia

Este proyecto se distribuye bajo la licencia [MIT](LICENSE).
