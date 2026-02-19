# Roadmap

## M1 — Foundations
**Scope**
- Project scaffolding, Docker, PostgreSQL, Redis.
- Base architecture folders and policies scaffold.
- Seed data for demo.

**Acceptance**
- App boots in Docker.
- DB migrations run in Postgres.

**Risks / Tradeoffs**
- Local PHP version mismatch; rely on Docker.

## M2 — Communities + Feed
**Scope**
- Community model, migrations, CRUD.
- Feed endpoint with pagination and basic ranking.

**Acceptance**
- `/api/v1/communities` and `/api/v1/feed` responses paginated.

**Risks / Tradeoffs**
- Ranking may be naive until M4.

## M3 — Posts + Nested Comments
**Scope**
- Posts and comments endpoints.
- Nested comment tree retrieval with depth limit.

**Acceptance**
- Create/read posts and comments with depth control.

**Risks / Tradeoffs**
- Adjacency list requires careful query patterns.

## M4 — Votes + Ranking
**Scope**
- Voting endpoints with idempotency.
- Ranking algorithm v1.

**Acceptance**
- One vote per user per item enforced.

**Risks / Tradeoffs**
- Score updates must be race-safe.

## M5 — Profiles + Activity
**Scope**
- User profile endpoint.
- Activity listing for posts/comments/votes.

**Acceptance**
- Profile data and recent activity list visible.

**Risks / Tradeoffs**
- Activity joins could become expensive.

## M6 — Hardening + Observability
**Scope**
- Rate limiting, logs, correlation IDs.
- Healthcheck, metrics, CI checks.

**Acceptance**
- Basic health endpoint and CI pipeline green.

**Risks / Tradeoffs**
- Observability overhead; keep minimal.
