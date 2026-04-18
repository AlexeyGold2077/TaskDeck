# TaskDeck

TaskDeck is a Spring Boot REST API for managing projects and tasks with JWT-based authentication.

The project already includes:
- user registration and login
- protected endpoints with JWT
- project creation, listing, viewing, and deletion
- task creation, listing, status updates, and deletion

## Tech Stack

- Java 17
- Spring Boot 4
- Spring Web MVC
- Spring Security
- Spring Data JPA
- PostgreSQL
- JWT (`jjwt`)
- Lombok
- Maven

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

## Features

### Authentication
- `POST /auth/register` registers a new user
- `POST /auth/login` authenticates a user and returns a JWT token

### Projects
- `POST /projects` creates a new project
- `GET /projects` returns all projects for the authenticated user
- `GET /projects/{id}` returns a single project by id
- `DELETE /projects/{id}` deletes a project by id

### Tasks
- `POST /projects/{projectId}/tasks` creates a task inside a project
- `GET /projects/{projectId}/tasks` returns all tasks for a project
- `PATCH /tasks/{id}` updates task status
- `DELETE /tasks/{id}` deletes a task

## Requirements

Before запуском убедись, что у тебя установлены:
- Java 17+
- Maven 3.9+
- PostgreSQL

## Configuration

At the moment the application contains only a minimal `application.properties`, so database connection settings should be added locally before running the app.

Current file:

```properties
spring.application.name=taskdeck
server.port=8080

spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

Example configuration for local development:

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

## Run Locally

1. Create a PostgreSQL database, for example `taskdeck`.
2. Add datasource settings to `src/main/resources/application.properties`.
3. Start the application:

```bash
./mvnw spring-boot:run
```

If Maven Wrapper is unavailable in your environment, use:

```bash
mvn spring-boot:run
```

By default, the API runs on:

```text
http://localhost:8080
```

## Authentication Flow

1. Register a user with `POST /auth/register`
2. Log in with `POST /auth/login`
3. Copy the JWT token from the response
4. Pass it in the `Authorization` header for protected endpoints

Example header:

```http
Authorization: Bearer <your-jwt-token>
```

## API Examples

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

### Get All Projects

```http
GET /projects
Authorization: Bearer <your-jwt-token>
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
  "status": "TODO",
  "dueDate": 1715000000
}
```

### Update Task Status

```http
PATCH /tasks/1
Authorization: Bearer <your-jwt-token>
Content-Type: application/json
```

```json
{
  "newStatus": "IN_PROGRESS"
}
```

## Domain Notes

- `createdAt` and `dueDate` are currently stored as Unix timestamps in integer form
- tasks support `priority` and `status` enums
- all endpoints except `/auth/**` require authentication

## Known Limitations

This project is already a solid backend foundation, but there are a few things worth improving next:
- JWT secret is hardcoded in the service and should be moved to environment variables or properties
- some service methods use `Optional.get()` and should return proper `404` responses instead
- access checks for user-owned resources can be strengthened
- there are currently no automated tests in the repository
- the repository contains macOS `.DS_Store` files that should be removed from version control

## Roadmap Ideas

- assign executors to tasks
- add task editing beyond status updates
- implement pagination and filtering
- add Swagger / OpenAPI documentation
- add Docker setup for app and database
- add unit and integration tests
- introduce refresh tokens or token revocation

## Author

Created by Alexey Gold.
