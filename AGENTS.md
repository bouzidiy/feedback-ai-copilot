# Repository Guidelines

## Project Structure & Module Organization

This repository contains the Feedback AI Copilot portfolio project. The active application code is in `apps/backend`, a Spring Boot backend organized with hexagonal architecture:

- `domain`: core feedback models, validation rules, domain exceptions, and outbound port interfaces required by the domain.
- `application`: use cases such as `CreateFeedbackUseCase`; orchestrates domain behavior through inbound use-case APIs and outbound ports.
- `infrastructure`: driving adapters such as web controllers, driven adapters such as persistence implementations, JPA entities, mappers, and global exception handling.
- `src/main/resources/db/migration`: Flyway database migrations.
- `src/test/java`: backend tests and Testcontainers configuration.

`apps/frontend` currently contains only a placeholder. Documentation lives in `docs`, and future deployment assets are grouped under `infra/docker` and `infra/terraform`.

## Build, Test, and Development Commands

Run backend commands from `apps/backend`:

- `./mvnw spring-boot:run`: start the Spring Boot API locally.
- `./mvnw test`: run the test suite.
- `./mvnw clean verify`: compile, test, and perform Maven verification; this matches CI.
- `docker compose up -d`: start the local PostgreSQL 17 service defined in `compose.yaml`.

Use the Maven wrapper instead of a system Maven install. The project targets Java 21+ in `pom.xml`; CI currently runs with Temurin Java 25.

## Coding Style & Naming Conventions

Use standard Java formatting with 4-space indentation. Keep package names lowercase under `com.yassine.feedbackaicopilot`. Name classes by responsibility, for example `FeedbackController`, `FeedbackRepositoryAdapter`, `CreateFeedbackRequest`, and `InvalidFeedbackException`.

Preserve the hexagonal architecture. Keep the domain and application core independent from infrastructure details: dependencies point inward, adapters depend on ports, and infrastructure implements or invokes those ports. Put API DTOs and controllers under `infrastructure/web`, persistence entities and adapters under `infrastructure/persistence`, use-case orchestration under `application`, and business rules under `domain`.

## Testing Guidelines

Tests use JUnit 5, Spring Boot test support, and Testcontainers for PostgreSQL integration. Place tests in the matching package under `apps/backend/src/test/java` and use the `*Test` suffix, such as `CreateFeedbackUseCaseTest`.

Prefer focused unit tests for domain and application behavior. Use Spring context or Testcontainers tests when persistence, migrations, or wiring are part of the behavior being verified. Run `./mvnw clean verify` before opening a pull request.

## Commit & Pull Request Guidelines

Recent history uses Conventional Commit-style messages, often with a story key, for example `feat(US-001): Create endpoint to save feedback` or `feat: fix ci`. Keep commits scoped and imperative.

Pull requests should include a short description, linked issue or user story when available, test results, and screenshots or API examples for user-facing changes. Update `README.md`, `ROADMAP.md`, or `docs` when behavior, architecture, or setup steps change.
