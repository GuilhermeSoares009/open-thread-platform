# Web

Frontend MVP do OpenThread consumindo a API Java diretamente (sem BFF).

## Stack
- Next.js
- TypeScript
- Tailwind CSS

## Rotas
- `/` home com feed e comunidades
- `/community/:id`
- `/post/:id`
- `/profile/:id`

## Configuracao
Copie `.env.example` para `.env.local` e ajuste se necessario:
- `NEXT_PUBLIC_API_BASE_URL`

## Rodar
- `pnpm install`
- `pnpm dev`

## Rodar via Docker
- Na raiz do repo: `make stack-up`
