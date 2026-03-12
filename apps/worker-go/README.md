# Worker Go

Worker assincrono para consumo de eventos de dominio publicados pela API Java.

## Funcao atual
- Consome stream Redis `openthread.events`
- Faz parse de eventos (`post.created`, `comment.created`, `vote.cast`)
- Mantem gancho para projecao de ranking assincrona

## Configuracao
Copie `.env.example` para `.env` (ou exporte variaveis):
- `REDIS_ADDR`
- `EVENT_STREAM_KEY`

## Rodar
- `go run ./cmd/worker`

## Rodar via Docker
- Na raiz do repo: `make stack-up`
