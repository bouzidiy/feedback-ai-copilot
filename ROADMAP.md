# Feedback AI Copilot — Roadmap

> **Goal:** demonstrate a maintainable feedback analysis application using Spring Boot, Angular and, later, RAG.
> **Last review:** November 2026. Checkboxes denote verified repository deliverables, not intentions.

## Existing foundation

- [x] Backend Maven scaffold: Java 21 target, Spring Boot 4.1, Web MVC, JPA, validation and Flyway dependencies.
- [x] Backend GitHub Actions build/test workflow.
- [ ] Angular application (frontend folder currently contains a placeholder only).
- [ ] Terraform infrastructure (Terraform folder currently contains a placeholder only).
- [ ] Spring AI integration (not present in the inspected backend dependencies).

## Now — milestone 1: minimal feedback API

- [ ] Implement Feedback entity and migration (id, text, source, createdAt).
- [ ] Provide `POST /api/feedback` and `GET /api/feedback` with input validation, pagination and error handling.
- [ ] Add integration tests with PostgreSQL/Testcontainers.
- [ ] Ensure documented Java version and CI runtime are compatible.
- [ ] Provide a reproducible local run command and example requests in the README.

**Next action:** implement one Feedback entity, its Flyway migration and a failing-then-passing persistence test.

## Next — milestone 2: Angular and delivery

- [ ] Angular frontend: submit feedback and display results/errors.
- [ ] Frontend build/test GitHub Actions job separate from backend validation.
- [ ] Local Docker Compose for PostgreSQL, then a deployment plan with cost/security notes.
- [ ] Update architecture diagram and README to match the runnable system.

## Later — milestone 3: useful AI feature

- [ ] Add Spring AI and a real model adapter (with tests around the integration boundary).
- [ ] Classify or summarize feedback; measure response quality on a sample dataset.
- [ ] Introduce pgvector/RAG **only if** retrieval genuinely improves the use case.
- [ ] Add observability and AWS/Terraform deployment after the local flow is stable.

## Exit criteria

Each milestone needs working code, a repeatable demo, automated tests and a short record of trade-offs. Avoid marking technologies « in use » before the repository demonstrates them.
