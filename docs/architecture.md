# Arquitectura

La aplicación sigue un enfoque de **arquitectura hexagonal**. Esta separación permite aislar la lógica de negocio de los detalles de infraestructura y facilita las pruebas y el mantenimiento.

## Flujo
1. Un cliente realiza una petición HTTP a un **controlador**.
2. El controlador actúa como adaptador de entrada y delega la operación a un **servicio**.
3. El servicio ejecuta el caso de uso en la capa de aplicación y se comunica con el **dominio**.
4. Cuando se necesitan recursos externos, se utilizan adaptadores de salida como **repositorios** o **clientes**.
5. La respuesta retorna por el mismo camino hasta el cliente.

## Funcionamiento general
- `Application` es el punto de entrada que arranca el contenedor de Spring.
- `UserController` expone los endpoints REST.
- `UserService` contiene la lógica de negocio.
- `UserRepository` gestiona la persistencia de datos.
