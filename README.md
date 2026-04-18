# TaskDeck

TaskDeck is a backend pet project for managing projects and tasks. The application is built with Spring Boot and provides a REST API with JWT authentication.

The project is still in progress, so the current README describes what is already implemented in the repository right now.

## Current Status

What already works:
- user registration and login
- JWT-based protection for private endpoints
- creating and viewing projects
- creating tasks inside a project
- changing task status
- deleting projects and tasks
- running with PostgreSQL locally or through Docker Compose

What is still incomplete or likely to change:
- no tests yet
- no Swagger / OpenAPI documentation
- minimal validation for some DTOs
- some endpoints still return very basic responses
- overall API and data model may still evolve

## Stack

- Java 17
- Spring Boot 4
- Spring Web MVC
- Spring Security
- Spring Data JPA
- PostgreSQL
- JWT (`jjwt`)
- Lombok
- Maven
- Docker Compose

## Project Structure

```text
src/main/java/com/alexeygold2077/taskdeck
├── config
├── controller
├── exception
├── mapper
├── model
│   ├── dto
│   ├── entity
│   └── util
├── repository
├── security
└── service
```

## API Overview

### Authentication

- `POST /auth/register` registers a new user
- `POST /auth/login` authenticates a user and returns a JWT token

### Projects

- `POST /projects` creates a new project
- `GET /projects` returns all projects of the authenticated user
- `GET /projects/{id}` returns a project by id
- `DELETE /projects/{id}` deletes a project by id

### Tasks

- `POST /projects/{projectId}/tasks` creates a task inside a project
- `GET /projects/{projectId}/tasks` returns all tasks for a project
- `PATCH /tasks/{id}` updates task status
- `DELETE /tasks/{id}` deletes a task

## Security

- `/auth/**` is public
- all other endpoints require a JWT token
- token should be sent in the `Authorization` header

Example:

```http
Authorization: Bearer <your-jwt-token>
```

## Request Examples

### Register

```http
POST /auth/register
Content-Type: application/json
```

```json
{
  "email": "alexey@example.com",
  "username": "alexey",
  "password": "strongpassword"
}
```

### Login

```http
POST /auth/login
Content-Type: application/json
```

```json
{
  "login": "alexey",
  "password": "strongpassword"
}
```

Example response:

```json
{
  "id": 1,
  "username": "alexey",
  "email": "alexey@example.com",
  "token": "eyJ..."
}
```

### Create Project

```http
POST /projects
Authorization: Bearer <your-jwt-token>
Content-Type: application/json
```

```json
{
  "name": "TaskDeck API",
  "description": "Backend for task management",
  "createdAt": 1715000000
}
```

Note: `createdAt` is present in the DTO, but the service currently overwrites it with the current server timestamp.

### Create Task

```http
POST /projects/1/tasks
Authorization: Bearer <your-jwt-token>
Content-Type: application/json
```

```json
{
  "name": "Implement JWT auth",
  "description": "Add login and token validation",
  "priority": "HIGH",
  "status": "NEW",
  "dueDate": 1715000000
}
```

Available values:
- `priority`: `LOW`, `MEDIUM`, `HIGH`
- `status`: `NEW`, `IN_PROGRESS`, `DONE`

### Update Task Status

```http
PATCH /tasks/1
Authorization: Bearer <your-jwt-token>
Content-Type: application/json
```

```json
{
  "newStatus": "DONE"
}
```

## Local Run

Requirements:
- Java 17+
- Maven 3.9+
- PostgreSQL

Minimal example for `src/main/resources/application.properties`:

```properties
spring.application.name=taskdeck
server.port=8080

spring.datasource.url=jdbc:postgresql://localhost:5432/taskdeck
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

Start the app:

```bash
./mvnw spring-boot:run
```

If needed:

```bash
mvn spring-boot:run
```

By default the API runs at `http://localhost:8080`.

## Run With Docker Compose

The repository already contains `compose.yaml`, `Dockerfile`, and `run.sh`.

Build the jar first:

```bash
./mvnw clean package
```

Then start containers:

```bash
docker compose up --build
```

Default container setup:
- app port: `8080`
- PostgreSQL port: `5432`
- database: `postgres_db`
- username: `user`
- password: `paswd`

## Notes

- The project currently looks like a backend foundation rather than a finished product.
- If you continue developing it, useful next steps would be tests, API documentation, better validation, and more consistent error handling.
