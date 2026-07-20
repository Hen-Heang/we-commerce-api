# Development Workflow

Required for every non-trivial task (skip the ceremony only for trivial, obviously-scoped one-liners — still explain what you're doing).

## Phase 1 — Requirement analysis

Identify: user goal, business behavior, acceptance criteria, affected domain, auth requirements, data changes, API contract changes, error/edge cases, doc impact, test impact.

State a reasonable assumption and continue when one is safe. Ask a question only when different answers would produce materially different implementations.

## Phase 2 — Codebase investigation

Inspect, in this order, whatever is relevant to the task: controller → service interface → service impl → repository → entity → DTOs → `SecurityConfiguration` (if auth-adjacent) → exception handling → existing tests → a similar existing implementation elsewhere in the codebase.

Explain the current flow before proposing changes. Remember `SecurityConfiguration`'s matchers are exact-path, not `/**` — verify actual role enforcement for the specific path in question rather than assuming from the prefix (see `.claude/rules/00-project-context.md`).

## Phase 3 — Architecture impact

Evaluate: controller/service/repository responsibility split, transaction boundaries, entity relationships (cascade, lazy-loading, N+1 risk), DTO compatibility, security impact, validation impact, performance impact, API compatibility, DB migration impact (no production migration tooling exists — `ddl-auto: update` is what's configured, flag any schema change), deployment impact.

## Phase 4 — Implementation plan (present before editing)

1. Files to modify
2. Files to create
3. Current behavior
4. Planned behavior
5. Implementation steps
6. Tests to add
7. Risks
8. Out-of-scope work

For larger work, phase it (e.g. DTO/validation → repository → service → controller → tests → docs). Don't bundle unrelated refactoring with a feature or fix.

## Phase 5 — Implementation

- Smallest coherent change; respect existing architecture and naming/package conventions unless fixing an approved inconsistency.
- Thin controllers, business logic in services, data access in repositories.
- Constructor injection, `final` dependencies, never field injection.
- Request/response DTOs only — never expose entities directly.
- `@Valid` on validated request DTOs.
- `@Transactional` on atomic multi-step writes.
- No magic strings, no duplicated mapping logic, no unnecessary abstraction, no broad `throws Exception`.
- Preserve backward compatibility unless a breaking change is explicitly approved.
- Update Swagger annotations and relevant docs when a contract changes.

## Phase 6 — Self-review

Review your own diff for: correctness, security, authorization, validation, transaction safety, null handling, error handling, API compatibility, DB behavior, performance, naming, readability, test coverage, and unrelated/accidental changes.

## Phase 7 — Verification

Run the checks in `.claude/rules/30-verification.md` appropriate to the change type. Report results honestly.

## Phase 8 — Final report

1. What changed
2. Why it changed
3. Files modified
4. Tests added
5. Commands executed
6. Results
7. Remaining risks
8. Recommended next step

## Domain-specific review triggers

**Authentication** — always check: password verification/encoding, access/refresh token validation, token type, expiration, revocation, multiple active sessions, logout behavior, correct 401 vs 403, no sensitive logging.

**Authorization** — always check: which `SecurityConfiguration` matcher (if any) actually covers the path being touched (remember the exact-path gap), role requirements, resource ownership, current-user lookup, unauthorized vs forbidden responses.

**Purchases** — always check: self-purchase prevention, product availability, product ownership, address ownership, transaction boundaries, receipt creation, notification creation, product status update, rollback behavior, duplicate-purchase race conditions.

**Files** — always check: file type, file size, filename sanitization, path traversal, storage persistence, public access via `/api/v1/fileView/**`, authorization, behavior across a container restart (no persistent volume assumptions unless confirmed).
