# Roadmap

Milestones are sequential and intended to deliver a usable demo at the end of each phase.

## M1 — Foundations
**Scope**
- Project scaffolding, Docker, PostgreSQL, Redis.
- Base architecture folders and policies scaffold.
- Seed data for demo.

**Deliverables**
- Containerized dev environment and base service wiring.
- Migration and seed workflows documented.

**Acceptance**
- App boots in Docker.
- DB migrations run in Postgres.
- Seed data loads without errors.

**Dependencies**
- None.

**Risks / Tradeoffs**
- Local PHP version mismatch; rely on Docker.

## M2 — Communities + Feed
**Scope**
- Community model, migrations, CRUD.
- Feed endpoint with pagination and basic ranking.

**Deliverables**
- Community CRUD endpoints with pagination.
- Feed endpoint with cursor or page pagination.
- Home layout aligned to Figma (sidebar, top bar, feed cards, stat cards).

**Acceptance**
- `/api/v1/communities` and `/api/v1/feed` responses paginated.
- Pagination parameters documented.
- Home screen renders with Figma-aligned layout and theme toggle.

**Dependencies**
- M1 complete.

**Risks / Tradeoffs**
- Ranking may be naive until M4.

## M3 — Posts + Nested Comments
**Scope**
- Posts and comments endpoints.
- Nested comment tree retrieval with depth limit.

**Deliverables**
- Post and comment create/read endpoints.
- Comment tree retrieval with depth control.
- Post detail UI aligned to Figma (cover, composer, badges, nested thread).

**Acceptance**
- Create/read posts and comments with depth control.
- Depth limit enforced on comment tree responses.
- Nested comments render with indentation and role badges.

**Dependencies**
- M2 complete.

**Risks / Tradeoffs**
- Adjacency list requires careful query patterns.

## M4 — Votes + Ranking
**Scope**
- Voting endpoints with idempotency.
- Ranking algorithm v1.

**Deliverables**
- Vote create/update endpoints with idempotent behavior.
- Ranking v1 applied to feed ordering.

**Acceptance**
- One vote per user per item enforced.
- Vote changes update ranking without duplication.

**Dependencies**
- M3 complete.

**Risks / Tradeoffs**
- Score updates must be race-safe.

## M5 — Profiles + Activity
**Scope**
- User profile endpoint.
- Activity listing for posts/comments/votes.

**Deliverables**
- Profile endpoint with public fields.
- Activity endpoint with recent actions.

**Acceptance**
- Profile data and recent activity list visible.
- Activity results paginated.

**Dependencies**
- M4 complete.

**Risks / Tradeoffs**
- Activity joins could become expensive.

## M6 — Hardening + Observability
**Scope**
- Rate limiting, logs, correlation IDs.
- Healthcheck, metrics, CI checks.

**Deliverables**
- Basic health endpoint and CI workflow.
- Structured logging with correlation IDs.

**Acceptance**
- Basic health endpoint and CI pipeline green.
- Rate limiting applied to public endpoints.

**Dependencies**
- M1-M5 complete.

**Risks / Tradeoffs**
- Observability overhead; keep minimal.
