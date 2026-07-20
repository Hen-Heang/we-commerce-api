# We Commerce API Handoff

## Current Objective

Set up the project's Claude Code configuration (context, boundaries, workflow, verification, continuity) — no application code changes.

## Current Branch

main

## Current Status

Configuration complete. No application code was touched.

## Completed Work

- Created `CLAUDE.md` (root instructions, imports `.claude/rules/*`), `.claude/rules/{00-project-context,10-boundaries,20-development-workflow,30-verification,40-continuity}.md`, `.claude/settings.json`, `.claude/commands/{onboard,plan,review,handoff}.md`, `docs/{AI_WORKFLOW,CODE_REVIEW_CHECKLIST,DEVELOPMENT_DECISIONS}.md`, this `HANDOFF.md`.
- Confirmed via reading the code (not assumed): `SecurityConfiguration` role matchers are exact-path, not `/**`, leaving nested sub-paths under `.anyRequest().authenticated()` instead of `hasRole("USER")`.

## Files Modified

`CLAUDE.md` (rewritten, scope narrowed to this repo only, prior frontend-repo references removed)

## Files Created

`HANDOFF.md`, `docs/AI_WORKFLOW.md`, `docs/CODE_REVIEW_CHECKLIST.md`, `docs/DEVELOPMENT_DECISIONS.md`, `.claude/settings.json`, `.claude/rules/00-project-context.md`, `.claude/rules/10-boundaries.md`, `.claude/rules/20-development-workflow.md`, `.claude/rules/30-verification.md`, `.claude/rules/40-continuity.md`, `.claude/commands/onboard.md`, `.claude/commands/plan.md`, `.claude/commands/review.md`, `.claude/commands/handoff.md`

## Tests Added

None (configuration-only task, no application code changed).

## Commands Executed

- `git status`, `git branch --show-current`, `git log --oneline -10` — inspection only
- `claude --version` → `2.1.215` (confirmed project-command and `@import` support)
- JSON validation of `.claude/settings.json` — valid

## Verification Results

- `.claude/settings.json` — valid JSON (executed and passed)
- No Gradle build/test run — not required for a docs/config-only change per `.claude/rules/30-verification.md`

## Important Decisions

- Kept `.claude/settings.local.json` untouched (user's local overrides, out of scope for shared project config).
- Did not fix the `SecurityConfiguration` exact-path matcher gap — documented it as a known issue only, per the "do not modify application code" restriction on this task.

## Known Problems

- `SecurityConfiguration` exact-path matcher gap (see `.claude/rules/00-project-context.md`) — real, unresolved, not part of this task's scope.
- Uncommitted in-progress changes exist on `AuthController`, `PhoneLoginRequest`, `RegisterRequest`, `AuthenticationService`, plus a new untracked `AuthenticationServiceTest.java` — left untouched, belongs to the user's separate in-progress work.

## Remaining Work

None for this configuration task. Optional future enhancement: a `PreToolUse` hook to hard-block dangerous commands (force-push, hard reset, secret-file reads) at the tool layer — deferred, see `docs/AI_WORKFLOW.md`.

## Exact Next Step

None queued. Next session should run `/onboard` to confirm the config loads as expected, then resume whatever the user's next feature/fix/review request is.

## Risks and Warnings

None introduced. The uncommitted auth-file changes noted above are unrelated to this task and should not be swept into any future commit involving `.claude/**` or docs.

## Relevant Files

`CLAUDE.md`, `.claude/rules/*.md`, `.claude/settings.json`, `.claude/commands/*.md`, `docs/*.md`

## Notes for the Next Session

This file's structure should stay stable — update it in place via `/handoff`, don't restructure it casually.

## Last Updated

2026-07-20
