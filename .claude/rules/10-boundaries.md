# Boundaries

## Repository scope

Work only inside this repository: `we-commerce-api` (the directory containing this file's `.claude/` parent).

Do not:
- Read, list, or edit any sibling repository (`we-commerce`, `we-commerce-frontend`, or any other directory outside this repo root), even if referenced by README or old CLAUDE.md content.
- Use `--add-dir` or otherwise widen the working directory on your own initiative.
- Read personal home-directory files, SSH keys, cloud credential files, browser profile/data, or Git credential stores.
- Read unrelated environment variables beyond the ones this project declares (`DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, `JWT_SECRET_KEY`, `JWT_EXPIRATION`, `JWT_REFRESH_EXPIRATION`, `PORT`).

If a task seems to require touching the frontend repo (checking a type, an API call shape, a DTO match), say so explicitly and ask the user rather than reading it yourself.

## Secret protection

Never display, log, copy, or commit the *values* of:
- Database passwords, JWT secrets, access/refresh tokens, API keys, private keys, cloud credentials, personal auth values, production environment values.

Never read or attempt to decode:
```text
.env
.env.local
.env.development.local
.env.test.local
.env.production
.env.production.local
*.pem
*.key
*.p12
*.pfx
id_rsa
id_ed25519
credentials.json
secrets.json
.run/**
.idea/dataSources*/**
```
`.env.example` and committed `application.yml` are fine to read — they contain placeholders/env-var references, not real values.

When referencing secrets, use the variable **name** only (`JWT_SECRET_KEY`), never a value. If a committed file appears to contain a real secret, stop and warn the user instead of continuing.

## Modification scope

For normal development work, only modify application files after: reading the relevant code, explaining current behavior, producing a plan, and getting explicit instruction to implement.

While *this specific configuration setup task* is active, edits are limited to:
```text
CLAUDE.md
HANDOFF.md
.claude/**
docs/AI_WORKFLOW.md
docs/CODE_REVIEW_CHECKLIST.md
docs/DEVELOPMENT_DECISIONS.md
```
No Java source, `build.gradle`, `application.yml`, `Dockerfile`, or `render.yaml` changes during config setup.

## Dangerous operations — require explicit user authorization first

```text
git commit
git push
git merge
git rebase
git cherry-pick
git tag
git reset
git clean
git checkout -- <file>
git restore <file>
git branch -D
git remote set-url
gh pr create
gh pr merge
docker push
render deploy
any database migration execution
```

## Never execute

```text
git push --force / -f
git reset --hard
git clean -fd / -fdx
rm -rf
Remove-Item -Recurse -Force
DROP DATABASE
DROP TABLE
TRUNCATE
DELETE without a verified restrictive WHERE
```

Never delete untracked files. Never discard user changes.

Before modifying anything, run `git status` and `git diff`. If unrelated user changes exist (e.g. in-progress edits to auth files), preserve them, do not fold them into a proposed commit, and tell the user they exist rather than working around them silently.
