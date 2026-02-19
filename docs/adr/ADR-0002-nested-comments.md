# ADR-0002: Nested Comments Strategy

## Status
Accepted

## Context
Comments require threaded replies with bounded depth and efficient retrieval.

## Decision
Use an adjacency list with `parent_id` and `depth` column. Fetch by post and assemble the tree in application code.

## Consequences
- Simple writes and easy moderation.
- Reads require careful ordering and batching.
- If performance degrades, consider nested sets or materialized paths later.
