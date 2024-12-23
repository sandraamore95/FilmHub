FilmHub

FilmHub es un proyecto diseñado para practicar y aprender a desarrollar aplicaciones basadas en microservicios utilizando tecnologías como Spring Boot, Spring Cloud, Eureka, Config Server y API Gateway. Este proyecto simula un sistema para la gestión de usuarios, películas y favoritos, implementando comunicación entre microservicios.

Tecnologías utilizadas

Spring Boot: Framework principal para el desarrollo de los microservicios.

Spring Cloud: Gestión de configuraciones distribuidas y descubrimiento de servicios.

Eureka Server: Registro y descubrimiento de microservicios.

Config Server: Centralización de configuraciones para los microservicios.

API Gateway: Gestión centralizada de las peticiones hacia los microservicios.

Maven: Gestión de dependencias y estructura multi-módulo.

Microservicios incluidos

User Service: Gestiona la información de los usuarios.

Movie Service: Permite operaciones relacionadas con películas.

Favorite Service: Administra los favoritos de los usuarios.

Config Server: Centraliza las configuraciones de todos los servicios.

Eureka Server: Proporciona registro y descubrimiento de los microservicios.

API Gateway: Actúa como intermediario entre los clientes y los microservicios.

Estructura del proyecto

El proyecto está organizado en una estructura multi-módulo de Maven:

filmhub/
|— api-gateway/
|— config-server/
|— eureka-server/
|— favorite-service/
|— movie-service/
|— user-service/

Instrucciones de instalación

Clona este repositorio:

git clone https://github.com/sandraamore95/FilmHub.git

Importa el proyecto en tu IDE como un proyecto Maven.

Ejecuta los microservicios en el siguiente orden:

Config Server

Eureka Server

Los demás microservicios: User Service, Movie Service, Favorite Service, API Gateway.

Asegúrate de que cada microservicio pueda acceder a la configuración proporcionada por el Config Server.
