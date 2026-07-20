---
description: Review the current uncommitted git diff and report findings, without modifying files
---

Read-only. Make no file changes.

1. Run `git status`, `git diff --stat`, and `git diff`.
2. Review against `.claude/rules/30-verification.md`'s severity model and `docs/CODE_REVIEW_CHECKLIST.md`.
3. Pay particular attention to whatever domain the diff touches, using the domain-specific checks in `.claude/rules/20-development-workflow.md` (auth, authorization, purchases, files) when relevant — including whether any new or changed route actually lands under one of `SecurityConfiguration`'s exact-path matchers or falls through to `anyRequest().authenticated()`.
4. Report findings first, ordered Critical → High → Medium → Low. For each: severity, file, class/method, current behavior, impact, recommended fix, suggested test.
5. Only after findings, summarize anything that looks correct and well-handled.

Do not fix anything unless the user asks you to after seeing the review.
