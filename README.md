```markdown name=README.md url=https://github.com/clencyc/MinimalSpringbootAPI/blob/main/README.md
# MinimalSpringbootAPI

A small Kotlin + Spring Boot example API that manages simple notes. The project demonstrates a minimal layered architecture with a controller, service, and an in-memory repository, and includes unit/web-layer tests using MockK and Spring testing utilities.

- Language: Kotlin
- Framework: Spring Boot
- JVM target: Java 21
- Default branch: main

## Features

- GET /notes — list all notes
- POST /notes — create a new note
- PATCH /notes — update an existing note
- DELETE /notes/{noteId} — delete a note
- In-memory repository (for demo/testing)
- Unit tests for repository and service; controller tests using MockMvc and springmockk

## Model

Note (JSON)
- id: String
- title: String
- description: String

Example:
```json
{
  "id": "1",
  "title": "Groceries",
  "description": "Milk and eggs"
}
```

## Prerequisites

- JDK 21
- Gradle (recommended to use the wrapper if present)
- Internet access to download dependencies from Maven Central

The project uses:
- Kotlin (Kotlin JVM plugin)
- Spring Boot 4.x
- Jackson Kotlin module
- Testing: JUnit 5, MockK, springmockk

## Build & run

From the repository root:

- Build
  - ./gradlew build

- Run
  - ./gradlew bootRun
  - Or build the jar and run:
    - ./gradlew bootJar
    - java -jar build/libs/MinimalSpringbootAPI-0.0.1-SNAPSHOT.jar

By default the app runs on the Spring Boot default port (8080) unless overridden by environment or application properties.

## API usage

Base path: /notes

- GET /notes
  - Returns: 200 OK, JSON array of notes
  - Example:
    - curl http://localhost:8080/notes

- POST /notes
  - Body: JSON note object (id, title, description)
  - Returns: 200 OK with created note (in this minimal example the repository will throw if ID already exists)
  - Example:
    - curl -X POST -H "Content-Type: application/json" -d '{"id":"1","title":"Groceries","description":"Buy milk"}' http://localhost:8080/notes

- PATCH /notes
  - Body: full JSON note object to replace the existing note with the same id
  - Returns: 200 OK with updated note (or 400 if invalid)
  - Example:
    - curl -X PATCH -H "Content-Type: application/json" -d '{"id":"1","title":"Groceries v2","description":"Milk, eggs"}' http://localhost:8080/notes

- DELETE /notes/{noteId}
  - Deletes the note with the given id
  - Returns: 200 OK on success or 404 Not Found if note id does not exist
  - Example:
    - curl -X DELETE http://localhost:8080/notes/1

Error handling:
- 404 returned for NoSuchElementException (e.g., delete of missing id)
- 400 returned for IllegalArgumentException (e.g., add/patch with conflicting/missing data)

## Tests

Run tests with:
- ./gradlew test

Tests included:
- Unit tests for NoteRepository and NoteService
- Controller tests for NoteController using MockMvc and springmockk

## Project layout (key files)

- src/main/kotlin/clency/dev/notetaking/
  - NotetakingApplication.kt — Spring Boot application entry point
  - controllers/NoteController.kt — REST controller mapping /notes endpoints
  - services/NoteService.kt — business logic delegating to repository
  - dataSources/NoteRepository.kt — simple in-memory repository
  - model/Note.kt — note data class (id, title, description)

- src/test/kotlin/... — unit and controller tests (MockK, springmockk, MockMvc)

- build.gradle.kts — Kotlin Gradle build (Kotlin plugin, Spring Boot 4.x, Java toolchain configured to Java 21)

## Contributing

- Feel free to open issues or pull requests.
- If you plan to extend this project:
  - Consider replacing the in-memory repository with a persistent data source (JPA, R2DBC, or another DB).
  - Add validation for incoming payloads (e.g., with javax.validation / Spring Validation).
  - Add integration tests and a proper API contract (OpenAPI/Swagger).

## LICENSE

- MIT

## Contact

Repository: https://github.com/clencyc/MinimalSpringbootAPI

---
```
