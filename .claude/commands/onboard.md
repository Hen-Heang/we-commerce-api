---
description: Explain the current project state without modifying anything
---

Read-only. Make no file changes.

1. Read `CLAUDE.md` and every file in `.claude/rules/`.
2. Run `git status`, `git branch --show-current`, and `git log --oneline -10`.
3. Report:
   - Project purpose and current architecture (from `.claude/rules/00-project-context.md`, confirmed against actual code if anything looks stale)
   - Current branch and working-tree state, including any uncommitted or untracked changes and what they appear to be
   - Known architectural inconsistencies worth keeping in mind
   - Where `HANDOFF.md` and `docs/DEVELOPMENT_DECISIONS.md` stand, if they have content beyond the template

If the user gave a specific area to focus on (e.g. "explain the purchase flow"), trace that flow through controller → service → repository → entity and explain it concretely with file:line references, still without editing anything.
