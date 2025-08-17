# Foro-Hub
Challenge Alura
API REST de tópicos construida con Spring Boot 3, JPA, Flyway, MySQL y JWT.

Endpoints principales
POST /login → genera token JWT. Body: {"username":"admin@forumhub.com","password":"123456"}
POST /topicos → crea un tópico (requiere Authorization: Bearer <token>)
GET /topicos → lista tópicos (paginado, orden y filtros opcionales)
GET /topicos/{id} → detalle por id
PUT /topicos/{id} → actualiza
DELETE /topicos/{id} → elimina
Filtros opcionales en /topicos
page (default 0), size (default 10), asc (default true)
curso (contiene, case-insensitive)
anio (ej. 2025)
Reglas de negocio
Todos los campos son obligatorios.
No se permiten duplicados por (titulo, mensaje).
Configuración rápida
Ajusta spring.datasource.* y jwt.* en src/main/resources/application.properties.
mvn spring-boot:run
Autentica en POST /login y usa el token en las demás solicitudes.
Usuario en memoria por defecto: admin@forumhub.com / 123456 (cámbialo en application.properties).
