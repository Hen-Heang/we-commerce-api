---
description: Update HANDOFF.md with this session's progress, decisions, and next step
---

1. Review this session's actual work: `git status`, `git diff --stat`, and what was actually run and verified (not what was merely discussed).
2. Update `HANDOFF.md` in place, following its existing section structure. Be concrete:
   - Completed Work vs. Remaining Work must be clearly separated
   - Tests Added / Commands Executed / Verification Results must state exact outcomes (pass/fail/not run), not assumptions
   - Exact Next Step must be a single concrete action, not "continue development"
   - No secret values anywhere in the file
3. If an important, durable architectural decision was made this session (not just discussed), add an entry to `docs/DEVELOPMENT_DECISIONS.md` using its template.
4. Do not commit these updates unless the user explicitly asks.
