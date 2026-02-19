# OpenThread Specification (Spec Kit)

## Product Vision
OpenThread is a community-driven discussion platform focused on fast discovery and high-signal conversations through ranked feeds, nested comments, and voting.

## Functional Requirements
- Communities with public profiles and metadata.
- Paginated feed with ranking and optional community filter.
- Posts with nested comments (reply depth limit).
- Voting on posts and comments with idempotent behavior.
- User profiles with basic activity view.
- Comment and reply input with validation.

## Non-Functional Requirements
- Performance: pagination on all lists, explicit indexes, avoid N+1, define p95 budget.
- Security: strict validation, authorization policies, rate limiting, IDOR protection.
- Observability: correlation ID, structured logs, healthcheck, basic metrics.
- Idempotency: vote operations must be safe to retry.

## Use Cases
- Browse feed (global or by community).
- Read a post and its comment thread.
- Create a post in a community.
- Reply to a post or to another comment.
- Upvote/downvote a post or comment.
- View a user profile and recent activity.

## Business Rules
- One vote per user per votable item.
- Maximum comment depth is enforced.
- Content length limits are enforced for posts and comments.
- Soft delete for posts and comments; votes are removed on hard delete.

## API Contracts
- Versioned base path: `/api/v1`.
- OpenAPI definition: `docs/api/openapi.yaml`.
- Error format uses `application/problem+json`.

## Error Standard (Problem Details)
- `type`, `title`, `status`, `detail`, `instance`, `errors`.
- Field errors use `errors` with `{ field: [messages] }`.

## Tech Limits
- Comment depth: 6 (configurable).
- Post length: 8,000 chars.
- Comment length: 2,000 chars.
- Page size: 10–50.

## Feature Flags
- Ranking algorithm evolution guarded by env/config flag.
- Cache/ranking optimizations toggled by env/config flag.
