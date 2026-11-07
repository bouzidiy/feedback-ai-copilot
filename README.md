# Feedback AI Copilot

**Status: early development / backend scaffold.** This is a learning and portfolio project aiming to build a feedback analysis application, rather than a production-ready AI platform today.

## Purpose

Build a simple feedback collection and analysis platform and then introduce AI features only when the application's core workflow is proven.

## Verified repository content (November 2026)

| Area | Current state |
|---|---|
| Backend | `apps/backend/`: Java target 21, Spring Boot 4.1, Maven; dependencies for Web MVC, JPA, validation, Flyway and PostgreSQL |
| CI | `.github/workflows/ci.yml`: backend build and tests; workflow configures Java 25 |
| Frontend | `apps/frontend/`: placeholder, Angular app not yet committed |
| Local/cloud infrastructure | `infra/docker/` and `infra/terraform/`: placeholders, no deployed architecture demonstrated |
| AI | Spring AI and pgvector integration are **planned**, not yet implemented |

The build uses Java 25 in CI while the Maven project targets Java 21. This should be checked and standardized as part of the next milestone; it does not by itself demonstrate a build failure.

## Planned architecture

- **Backend:** Spring Boot, PostgreSQL, Flyway, tests.
- **Frontend:** Angular, then PWA/IndexedDB if offline requirements justify them.
- **AI:** Spring AI; optionally pgvector/RAG based on a measured use case.
- **Delivery:** separate CI workflows and, later, AWS with Terraform.

## Development

The backend is in `apps/backend/`. Check the current Maven Wrapper and local PostgreSQL configuration before running it. Frontend and Terraform setup instructions will be added once implemented.

## Next milestone

Implement a minimal Feedback CRUD API with a Flyway migration and integration tests, then document a reproducible local demo.

Follow the concise [ROADMAP](ROADMAP.md) for the complete order of work.
