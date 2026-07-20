# Continuity

Progress and decisions persist across sessions via two files, not via chat memory:

- `HANDOFF.md` — current session state: objective, status, what's done, what's next.
- `docs/DEVELOPMENT_DECISIONS.md` — a decision log for choices that matter beyond one session (auth design, response-envelope direction, transaction strategy, file-storage approach, JPA-vs-MyBatis, API-breaking changes, deployment architecture).

## When to update

- Update `HANDOFF.md` only when the user asks for a handoff, pack, session summary, or continuity update — not after every message. Use the `/handoff` command for this.
- Append to `docs/DEVELOPMENT_DECISIONS.md` when an important, durable architectural decision is actually made (accepted, not just discussed) — not as a daily activity log.

## Rules

- No secrets in either file.
- Don't record speculation as a decision — only record what was actually decided.
- Clearly separate completed work from planned work in `HANDOFF.md`.
- Include exact test/build results (pass/fail, not "should pass").
- Include an exact, concrete next step — not "continue development."
- At the start of a session, if `HANDOFF.md` exists and has content beyond the template, read it before starting new work so you don't repeat or contradict prior decisions.
