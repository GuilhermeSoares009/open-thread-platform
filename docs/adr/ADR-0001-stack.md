# ADR-0001: Stack Selection

## Status
Accepted

## Context
We need a modern PHP stack with strong ecosystem support and rapid delivery for a community platform.

## Decision
Use PHP 8.4, Laravel 12, FilamentPHP, PostgreSQL 16, SQLite (dev opt), Redis, Docker, Tailwind CSS, and Blade.

## Consequences
- Fast delivery with a mature ecosystem.
- Clear separation between dev (SQLite) and prod-like (PostgreSQL).
- Requires Docker for consistent PHP 8.4 runtime.
