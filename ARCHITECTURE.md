# Arquitectura Hexagonal

## Objetivo
La arquitectura hexagonal (o de puertos y adaptadores) busca aislar la lógica de negocio de los detalles de implementación para facilitar la mantenibilidad, pruebas y evolución del sistema.

## Capas principales

### Domain
Contiene el modelo y las reglas de negocio puras. No depende de capas externas.

### Application / Use Case
Orquesta los casos de uso de la aplicación y define los puertos que permiten la comunicación con el exterior.

### Infrastructure
Implementa detalles técnicos como persistencia, mensajería o integración con frameworks externos.

### Adapters
Componentes que implementan los puertos definidos en la capa de aplicación. Pueden ser de entrada (por ejemplo, controladores HTTP) o de salida (por ejemplo, repositorios o clientes de servicios externos).

## Lineamientos de comunicación
- La comunicación entre capas se realiza mediante **puertos** (interfaces) y **adaptadores**.
- Los puertos definen contratos de entrada y salida; los adaptadores los implementan.
- Las dependencias siempre apuntan hacia el **domain**, evitando acoplarlo a frameworks o tecnologías específicas.
- Los adaptadores de entrada convierten solicitudes externas en llamadas a casos de uso; los adaptadores de salida transforman respuestas del dominio a formatos externos.

## Referencias
- [Alistair Cockburn - Hexagonal Architecture](https://alistair.cockburn.us/hexagonal-architecture/)
- [Wikipedia - Hexagonal architecture](https://en.wikipedia.org/wiki/Hexagonal_architecture_(software))
