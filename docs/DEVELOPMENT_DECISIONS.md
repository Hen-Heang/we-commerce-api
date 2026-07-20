# Development Decisions

A log of durable architectural decisions for `we-commerce-api`. Not a daily activity log — only record decisions that are actually accepted and will shape future work.

## Decision: Establish structured Claude Code configuration for this repo

- Date: 2026-07-20
- Status: Accepted
- Context: The repo had an ad-hoc but already fairly thorough root `CLAUDE.md` mixing this repo and the sibling frontend repo's scope, no `.claude/rules/`, no `.claude/commands/`, no continuity files.
- Decision: Split instructions into `CLAUDE.md` (concise entry point, imports rule files) + `.claude/rules/*.md` (context, boundaries, workflow, verification, continuity) + `.claude/commands/*.md` (`/onboard`, `/plan`, `/review`, `/handoff`) + `docs/{AI_WORKFLOW,CODE_REVIEW_CHECKLIST,DEVELOPMENT_DECISIONS}.md` + `HANDOFF.md`. Scoped strictly to `we-commerce-api`; frontend-repo references removed.
- Alternatives: Keep the single flat `CLAUDE.md` — rejected, harder to maintain as rules grow and mixes concerns (config setup vs. feature work) in one file.
- Consequences: Rule files must be kept in sync with `CLAUDE.md`'s `@import` list; any new rule file needs to be added there explicitly or it won't load.
- Related files: `CLAUDE.md`, `.claude/rules/*.md`

<!-- Add new decisions above this line, using the template below. -->

## Decision: <title>

- Date:
- Status: Proposed | Accepted | Superseded
- Context:
- Decision:
- Alternatives:
- Consequences:
- Related files:
