# Code Review Checklist

For use by Claude (`/review`) and humans reviewing PRs in `we-commerce-api`. Severity model is defined in `.claude/rules/30-verification.md` (Critical / High / Medium / Low).

## Architecture

- [ ] Controller stays thin — HTTP concerns only, no business logic
- [ ] Business logic lives in the service layer
- [ ] Data access lives in the repository layer
- [ ] New code follows the existing `service/<Feature>/` + `repository/<Feature>/` package convention
- [ ] No unrelated refactoring bundled with the change

## Java

- [ ] Constructor injection, `final` dependencies, no field injection
- [ ] No broad `throws Exception`
- [ ] `Optional` used appropriately, no unnecessary `null` returns
- [ ] No dead code or commented-out blocks left behind

## Spring Boot

- [ ] Correct HTTP status codes for success and failure paths
- [ ] Response uses an existing envelope (`ApiResponse` or the established feature-specific shape) — no new ad-hoc shape introduced without discussion
- [ ] `@Transactional` on atomic multi-step writes, at the service layer
- [ ] Centralized exception handling used (an `exception/` handler), not local try/catch swallowing

## Security

- [ ] New/changed route is covered by the correct `SecurityConfiguration` matcher — verify it isn't an exact-path match that misses a nested sub-path (known gap, see `.claude/rules/00-project-context.md`)
- [ ] Ownership check present for any resource the endpoint mutates or returns
- [ ] No password, token, or secret value logged or returned in a response
- [ ] 401 vs 403 used correctly

## JWT

- [ ] Token type checked where relevant (access vs refresh)
- [ ] Expiration checked
- [ ] Revocation checked against `TokenRepository`, not signature/expiry alone
- [ ] No token persisted or logged in plaintext outside its intended storage

## API

- [ ] Endpoint path, field names, status codes, and response structure unchanged unless a breaking change is explicitly approved
- [ ] Swagger/OpenAPI annotations updated to match

## Validation

- [ ] Request DTOs use `@Valid` / Bean Validation annotations
- [ ] Validation failures produce a consistent error shape via the existing exception handlers

## Transactions

- [ ] Multi-step writes (e.g. purchase → receipt → notification → product status) are wrapped in a single `@Transactional` boundary
- [ ] Rollback behavior considered for partial failure

## JPA

- [ ] Owning side of relationships reviewed
- [ ] Cascade behavior reviewed (no accidental cascading deletes)
- [ ] Lazy-loading and N+1 query risk reviewed
- [ ] No reliance on `ddl-auto: update` as a migration strategy for anything beyond local dev
- [ ] Entities never returned directly from a controller

## PostgreSQL

- [ ] Queries parameterized (no string-concatenated SQL)
- [ ] Indexes/uniqueness constraints considered for new columns used in lookups

## File handling

- [ ] File type validated
- [ ] File size validated (multipart max is 50000KB per `application.yml`)
- [ ] Filename sanitized, no path traversal possible
- [ ] Public access via `/api/v1/fileView/**` is intentional for the file in question

## Testing

- [ ] Bug fixes include a regression test
- [ ] Security changes include auth/authz tests (allowed role, 401, 403, invalid/revoked token)
- [ ] Multi-step writes include a rollback test
- [ ] Tests are deterministic, no dependency on production data

## Documentation

- [ ] `docs/DEVELOPMENT_DECISIONS.md` updated if a durable architectural decision was made
- [ ] `.claude/rules/00-project-context.md` updated if this change resolves a documented known inconsistency

## Git safety

- [ ] Diff contains only the intended files — no unrelated in-progress work swept in
- [ ] No secret values in the diff
- [ ] Commit message follows Conventional Commits
