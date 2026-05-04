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
│   └── entity
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
- `DELETE /projects/{id}` deletes a project by id and returns `204 No Content`

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
  "description": "Backend for task management"
}
```

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
  "dueDate": "2024-05-06T11:06:40Z"
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
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
app.jwt.secret=your-long-random-secret
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

Set `APP_JWT_SECRET`, then start containers:

```bash
export APP_JWT_SECRET=your-long-random-secret
docker compose up --build
```

Default container setup:
- app port: `8080`
- PostgreSQL port: `5432`
- app health endpoint: `http://localhost:8080/actuator/health`
- database: `postgres_db`
- username: `user`
- password: `paswd`
