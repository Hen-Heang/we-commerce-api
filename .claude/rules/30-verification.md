# Verification

Never claim success without executing the relevant verification. Distinguish explicitly between:
```text
Executed and passed
Executed and failed
Not executed
Not available
Blocked by environment
```
Never say "all tests pass," "the build is successful," or "production ready" unless you actually ran the command and saw that result. When a command fails: show the important error, explain the likely cause, separate environment failures from code failures, don't hide it, and don't weaken or delete a test just to turn the build green.

## Commands (this environment is Windows/PowerShell — use `.bat`; use the Unix form only if the user is explicitly in Git Bash/WSL)

```powershell
.\gradlew.bat test
.\gradlew.bat clean build
.\gradlew.bat test --tests "fully.qualified.TestClassName"
```
```bash
./gradlew test
./gradlew clean build
```

## Verification level by change type

**Documentation-only changes** (`.md`, `docs/`, `.claude/**`): `git diff --check`, `git diff` review — check Markdown structure, valid JSON where applicable, referenced paths resolve, no secret values, no broken syntax. No Gradle run needed.

**Java code changes**: targeted test for the touched area, then the full suite, then `clean build`.

**Security changes** (`SecurityConfiguration`, `JwtAuthenticationFilter`, `JwtService`, `LogoutService`, anything auth/authorization): require an authenticated-allowed-role test, an unauthenticated (401) test, a wrong-role (403) test, and an invalid/expired/revoked-token test where relevant.

**Database write changes**: require a success test, a validation-failure test, a not-found test, an ownership test, a rollback test, and note any duplicate/concurrency consideration (no unique constraint or optimistic locking should be assumed present unless confirmed in the entity).

**API contract changes**: controller test, service test, validation test, a Swagger/annotation review, and a response-status review.

## Diff review before declaring anything complete

```bash
git status
git diff --stat
git diff
git diff --check
```
Check for: secrets, debug code (`System.out.println`, leftover logging of sensitive fields), temp files, unrelated changes, generated files, missing tests, accidental formatting of untouched files.

## Finding severity (for code review / self-review)

- **Critical**: auth bypass, authz bypass, secret exposure, password exposure, data loss/corruption, unsafe file access, unrecoverable transaction failure.
- **High**: missing ownership validation, missing transaction, incorrect token validation, broken API contract, major production failure, serious race condition.
- **Medium**: missing validation, inefficient query, inconsistent response status, poor exception handling, meaningful testing gap.
- **Low**: naming inconsistency, minor duplication, doc issue, readability.

Every finding: severity, file, class/method, current behavior, impact, recommended fix, suggested test.

## Git and commit gate

Before proposing a commit: review the full diff, confirm no unrelated files are swept in (check for the in-progress auth-file edits currently on this branch — don't bundle them into an unrelated commit), confirm tests/builds actually ran, report anything failed or skipped, suggest a Conventional Commit message, then **wait for explicit permission** before running `git commit`. Never commit or push automatically.
