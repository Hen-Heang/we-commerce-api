# AI Workflow — How Claude Code Is Configured For This Repo

This documents the Claude Code configuration in `we-commerce-api`: what it does, why, and how to change it. Installed version confirmed at setup time: `2.1.215` (supports Markdown `@path` imports in `CLAUDE.md`, project slash commands under `.claude/commands/*.md`, and the `permissions.allow/deny/ask` settings schema used below).

## 1. What this configuration does

Makes Claude behave like a careful senior Spring Boot engineering partner scoped to this repo: understands the real architecture (not stale docs), won't wander into sibling repos or touch secrets, requires a plan before implementation, requires actual verification before claiming success, and preserves context across sessions via `HANDOFF.md` / `docs/DEVELOPMENT_DECISIONS.md`.

## 2. Context rules (`.claude/rules/00-project-context.md`)

Ground truth about the stack, architecture, package layout, and — importantly — the known inconsistencies already found by reading the code (the `SecurityConfiguration` exact-path matcher gap, the non-uniform response envelope, and the fact that auth files currently have uncommitted in-progress edits). Source-of-truth order: implementation → tests → build config → project docs → README → assumptions.

## 3. Repository boundaries (`.claude/rules/10-boundaries.md`)

Claude works only inside `we-commerce-api`. It will not read or edit sibling repositories (including `we-commerce-frontend`), personal/home-directory files, SSH keys, or cloud credentials, and will not widen its working directory on its own.

## 4. Permission behavior (`.claude/settings.json`)

Uses the project-level `permissions.allow` / `permissions.deny` / `permissions.ask` schema:
- **allow** (no prompt): read-only git inspection (`status`, `diff`, `log`, `show`, `branch --show-current`, `ls-files`, `grep`) and Gradle verification commands (`test`, `clean test`, `build`, `clean build`, `tasks`, `dependencies`), both `./gradlew`/`gradlew.bat` and PowerShell forms.
- **deny** (blocked outright, not just prompted): reading `.env*`, key/cert files, `.run/**`, `.idea/dataSources*/**`; running `git push --force`/`-f`, `git reset --hard`, `git clean -f*`, `rm -rf`, `Remove-Item -Recurse -Force`.
- **ask** (prompts every time): `git commit`, `git push`, `git merge`, `git rebase`, `git reset`, `git checkout`, `git restore`, `git branch -D`, `gh pr create/merge`, and running the app (`bootRun`).

Nothing broader than this is allowed — no blanket Bash/PowerShell allow, no permission-bypass mode. `.claude/settings.local.json` holds the user's own personal overrides and is intentionally left untouched by this configuration.

## 5. Secret protection

Never display, log, or commit real secret values (DB password, JWT secret, tokens, credentials). Refer to env vars by name only. `.env.example` and committed `application.yml` are safe to read (placeholders/env-var references only). File patterns matching real secrets are denied at the settings layer (see above) as a backstop to the rule-file instruction.

## 6. Required planning workflow (`.claude/rules/20-development-workflow.md`)

Every non-trivial task: requirement analysis → codebase investigation → architecture impact → implementation plan (presented, not silently executed) → implementation → self-review → verification → final report. Domain-specific checklists exist for authentication, authorization, purchases, and file handling.

## 7. Verification gates (`.claude/rules/30-verification.md`)

Claude must actually run `./gradlew test` / `.\gradlew.bat test` / `clean build` (matching the current shell) before claiming a change works, and must say explicitly whether a check was executed-and-passed, executed-and-failed, not executed, or blocked. Documentation-only changes get a lighter `git diff --check` pass instead of a full Gradle run.

## 8. Code-review severity

Critical / High / Medium / Low, defined in `.claude/rules/30-verification.md` and applied via the checklist in `docs/CODE_REVIEW_CHECKLIST.md`. Every finding must include file, location, current behavior, impact, fix, and a suggested test.

## 9. Git restrictions

No commit/push/merge/rebase/reset/branch-delete without explicit request, ever. Before proposing a commit, Claude reviews the full diff, confirms tests/build actually ran, and proposes a Conventional Commit message — then waits.

## 10. Handoff workflow

`HANDOFF.md` and `docs/DEVELOPMENT_DECISIONS.md` carry context between sessions. `HANDOFF.md` is updated only via `/handoff` (or explicit request), not after every message, to keep it meaningful rather than noisy. `docs/DEVELOPMENT_DECISIONS.md` only gets an entry when a decision is actually accepted.

## 11. How to update this configuration

Edit the relevant file directly:
- Stack/architecture facts change → `.claude/rules/00-project-context.md`
- Scope/permission changes → `.claude/rules/10-boundaries.md` and `.claude/settings.json` together (keep them consistent)
- Process changes → `.claude/rules/20-development-workflow.md`
- Verification requirement changes → `.claude/rules/30-verification.md`
- New/changed slash command → add or edit a file under `.claude/commands/`

If you add a new rule file, also add its `@.claude/rules/<name>.md` import line to `CLAUDE.md`, or it will not load automatically.

## 12. What is intentionally not automated

No `PreToolUse` hook is installed yet. A hook to hard-block force-push/hard-reset/secret-file-reads/writes-outside-repo at the tool-execution layer (rather than relying on Claude following the rules) would be a reasonable future addition, but wasn't added now to avoid shipping something untested against this Windows/PowerShell + Git Bash environment. The `permissions.deny` list in `.claude/settings.json` covers the same set of dangerous commands in the meantime, just at the permission-prompt layer rather than a hard block.

## Example workflows

### Explain a feature
```text
/onboard
Explain the complete purchase flow without modifying files.
```

### Plan a feature
```text
/plan
Add registration validation. Do not edit files yet.
```

### Implement after review
```text
Implement the approved registration-validation plan.
Run targeted tests and the full backend build.
```

### Review changes
```text
/review
Review the current Git diff. Report findings before summaries.
Do not modify files.
```

### Save session context
```text
/handoff
Update HANDOFF.md with completed work, verification results,
remaining work, and the exact next step.
```
