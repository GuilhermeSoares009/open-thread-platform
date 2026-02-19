# OpenThread Platform

OpenThread is a community-driven discussion platform with communities, a ranked feed, nested comments, and voting.

## Objectives
- Communities with a global and per-community feed
- Posts with nested comments and replies
- Voting with ranking and idempotency
- Public user profiles and activity

## Non-functional requirements
- Pagination and explicit indexes to avoid N+1
- Authorization policies and rate limiting
- Correlation ID and structured logs

## Decisions and trade-offs
- Nested comments use adjacency list with depth limit (see ADR-0002)
- Ranking starts simple and evolves behind feature flags (see ADR-0003)

## Stack
- PHP 8.4, Laravel 12
- Blade + Tailwind CSS
- FilamentPHP (admin)
- PostgreSQL 16 (prod-like), SQLite (dev opt)
- Redis (cache/session)
- Docker

## Architecture
- Layered: Domain / Application / Infrastructure / Interface
- Events: `PostCreated`, `VoteCast`, `CommentAdded`
- Prepared for queues and feature flags

## Docs
- Spec: `docs/spec.md`
- OpenAPI: `docs/api/openapi.yaml`
- ADRs: `docs/adr/`
- Roadmap: `docs/roadmap.md`
- Backlog: `docs/backlog.md`
- Threat model: `docs/threat-model.md`
- PR checklist: `docs/pr-checklist.md`
- Design system (initial): `docs/design-system.md`

## Local setup

### Docker (recommended)
1) `cp .env.example .env`
2) `docker compose up -d`
3) `docker compose exec app composer install`
4) `docker compose exec app php artisan key:generate`
5) `docker compose exec app php artisan migrate`
6) `pnpm install`
7) `pnpm dev`

### Local (without Docker)
1) Ensure PHP 8.4, Composer, Node.js, and pnpm are installed
2) `cp .env.example .env`
3) `composer install`
4) `php artisan key:generate`
5) `php artisan migrate`
6) `pnpm install`
7) `pnpm dev`

## Quality
- Tests: `php artisan test`
- Lint: `vendor/bin/pint --test`
- Static analysis: `vendor/bin/phpstan analyse`

## Figma token
Set `FIGMA_TOKEN` in `.env`. Do not commit the token.

## Contributing
Use trunk-based development, small PRs, and follow `docs/pr-checklist.md`.
