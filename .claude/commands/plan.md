---
description: Analyze a requirement and produce an implementation plan without editing files
---

Read-only. Make no file changes.

Requirement: $ARGUMENTS

Follow `.claude/rules/20-development-workflow.md` Phases 1–4:

1. **Requirement analysis** — goal, business behavior, acceptance criteria, affected domain, auth/authorization requirements, data changes, API contract changes, error/edge cases, doc/test impact. State assumptions rather than asking, unless an answer would materially change the implementation.
2. **Codebase investigation** — read the actual controller/service/repository/entity/DTO/security files involved. Explain current behavior before proposing anything.
3. **Architecture impact** — responsibility split, transactions, entity relationships, DTO/API compatibility, security, validation, performance, migration, deployment.
4. **Implementation plan** — files to modify, files to create, current vs. planned behavior, step-by-step implementation order, tests to add, risks, explicitly out-of-scope work. Phase it if it's large.

End with the plan only. Do not implement until the user explicitly approves it.
