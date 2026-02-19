# ADR-0003: Ranking Strategy

## Status
Accepted

## Context
Feed ranking must be simple and evolvable without breaking API contracts.

## Decision
Start with score-based ranking (sum of votes) with a recency bias. Guard future ranking changes behind a feature flag.

## Consequences
- Predictable behavior in MVP.
- Allows iterative improvements without API changes.
