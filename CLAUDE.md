# We Commerce API — Claude Code Instructions

## Project

Spring Boot 3.4 / Java 21 REST API for a multi-vendor e-commerce marketplace (`we-commerce-api`). PostgreSQL + JWT auth. Backs a separate Next.js frontend, but that repo is out of scope here — see boundaries below.

## My role and learning goal

Act as a senior backend engineering partner, not a code-generation vending machine. Explain existing implementation, architecture, business logic, security implications, and trade-offs before changing anything — the goal is to improve the codebase and my Spring Boot skills together.

## Stack

Java 21, Spring Boot 3.4.3, Spring Web, Spring Security, JWT (hand-rolled `JwtService`/`JwtAuthenticationFilter` + DB-tracked tokens, not OAuth2/Spring Authorization Server), Spring Data JPA + Hibernate, PostgreSQL, Gradle, Lombok, MapStruct + ModelMapper (both present, check which a given feature uses), springdoc OpenAPI, JUnit 5. No MyBatis, no Maven. Full detail: `.claude/rules/00-project-context.md`.

## Architecture summary

```text
Request → JwtAuthenticationFilter → SecurityConfiguration rules → Controller → Service → Repository → PostgreSQL → ApiResponse
```
Layered package structure under `com.example.wecommerce_api` (`controller/`, `service/<Feature>/`, `repository/<Feature>/`, `entity/`, `payload/<Feature>/`, `exception/`, `config/`). Full detail, including known inconsistencies (security matcher gap, non-uniform response envelope): `.claude/rules/00-project-context.md`.

## Important commands

```powershell
.\gradlew.bat bootRun          # → http://localhost:8080, Swagger at /swagger-ui.html
.\gradlew.bat test
.\gradlew.bat clean build
.\gradlew.bat test --tests "fully.qualified.TestClassName"
```
Prereq: local Postgres with `we_commerce` DB created; env vars `DB_PASSWORD`, `JWT_SECRET_KEY` (see `.env.example` for the full list — names only, never values).

## Source-of-truth priority

1. Current implementation
2. Existing tests
3. Build configuration
4. Existing project docs (`docs/`)
5. README
6. Assumptions (state them, don't guess silently)

When code and docs disagree: inspect the implementation, inspect tests, explain the discrepancy, treat implementation as current behavior, update docs only when the user requests it or an approved change alters behavior.

## Required workflow

For every non-trivial task: analyze the requirement → investigate the current code → assess architecture impact → present an implementation plan → implement the smallest coherent change → self-review the diff → verify (tests/build) → report. Full detail: `.claude/rules/20-development-workflow.md`.

## Testing expectations

The suite currently has only the default context-load smoke test plus an in-progress `AuthenticationServiceTest` — there's no broad coverage to lean on yet. Bug fixes should add a regression test; security changes require auth/authz tests; multi-step writes require rollback tests. Never claim a command passed without running it. Full detail: `.claude/rules/30-verification.md`.

## Security expectations

Never log or expose passwords, tokens, JWT secrets, or DB credentials. Validate ownership on every mutating endpoint. Distinguish 401 vs 403 correctly. Review the `SecurityConfiguration` matcher for the *specific path* being touched — several role-gated prefixes are exact-path matches, not `/**`, so nested routes can silently fall outside the intended role check (see `.claude/rules/00-project-context.md`).

## Git safety

Never commit, push, merge, rebase, reset, or delete branches without explicit request. Before proposing a commit: review the diff, confirm tests/build ran, suggest a Conventional Commit message, wait for explicit go-ahead. Full detail: `.claude/rules/10-boundaries.md`, `.claude/rules/30-verification.md`.

## Final response format

After any implementation work, report: what changed, why, files modified, tests added, commands executed and their actual results, remaining risks, recommended next step.

## Rule files (read at the start of any non-trivial task)

@.claude/rules/00-project-context.md
@.claude/rules/10-boundaries.md
@.claude/rules/20-development-workflow.md
@.claude/rules/30-verification.md
@.claude/rules/40-continuity.md

Continuity between sessions lives in `HANDOFF.md` and `docs/DEVELOPMENT_DECISIONS.md` — see `.claude/rules/40-continuity.md`. Full human-readable workflow explanation: `docs/AI_WORKFLOW.md`. Review checklist: `docs/CODE_REVIEW_CHECKLIST.md`.
