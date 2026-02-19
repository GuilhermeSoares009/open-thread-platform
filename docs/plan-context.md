# Contexto de Planejamento (Referencia)

Este arquivo guarda o contexto acordado para o planejamento, para referencia futura.

## Papel e repositorio
- Papel: Staff Engineer + Tech Lead + Security Reviewer + Frontend System Designer.
- Repositorio alvo: https://github.com/GuilhermeSoares009/open-thread-platform
- Referencias:
  - Spec Kit: https://github.com/github/spec-kit
  - Figma: https://www.figma.com/design/dICOr6fD2S9KhUqaYGh3ZO/Fullstack-Challenge---3Pontos

## Ambiente e segredos
- Usando OpenCode local.
- MCP do Figma configurado.
- FIGMA_TOKEN sera definido via `.env`.
- Nao solicitar chaves no prompt.
- Nao expor segredos em codigo ou docs.

## Instrucao critica
- Antes de qualquer geracao, carregar e seguir `AGENTS.md` na raiz do repositorio.

## Stack definitiva
- PHP 8.4
- Laravel 12
- FilamentPHP (estavel)
- PostgreSQL 16 (prod-like)
- SQLite (dev opt)
- Blade + Tailwind CSS (ultima estavel)
- Redis (ultima estavel)
- Docker (ultima estavel)

## Ferramentas de qualidade
- PHPUnit >= 10
- Laravel Pint
- PHPStan ou Psalm
- OpenTelemetry PHP
- GitHub Actions
- Dependabot scan

## Fluxo de execucao obrigatorio
1) Fazer um plano inicial: arquitetura, especificacao (Spec Kit), modelagem de dominio, roadmap com milestones, componentizacao basica de frontend, design system estrutural inicial.
2) Pausar a execucao.
3) Solicitar confirmacao de que `FIGMA_TOKEN` foi colocado no `.env`.
4) Depois da confirmacao, tentar conectar ao Figma via MCP.
5) Confirmar explicitamente: "Conexao com Figma bem-sucedida" ou "Falha na conexao com Figma".
6) Se conexao OK: extrair estrutura real do design, reajustar plano com base nas telas, refinar modelagem + contratos + frontend, atualizar design system.

## Objetivo do produto
Construir OpenThread com:
- Comunidades
- Feed paginado
- Post com comentarios aninhados
- Sistema de votos
- Perfil de usuario
- Campo comentar/responder

## Artefatos para gerar (Spec Kit)
- Visao do produto
- Requisitos funcionais
- Requisitos nao funcionais
- Casos de uso
- Regras de negocio
- API Contracts (OpenAPI)
- Versionamento inicial (/api/v1)
- Padronizacao de erros

## Modelagem de dominio
Entidades:
- User
- Community
- Post
- Comment (arvore)
- Vote

Definir:
- Relacionamentos
- Integridade referencial
- Estrategia nested comments (trade-offs)
- Estrategia ranking
- Soft delete vs hard delete
- Seed data para demo

## Arquitetura
- Domain/Application/Infrastructure/Interface
- Controllers / Services / Policies / Jobs / Events
- DTOs claros
- Eventos internos: PostCreated, VoteCast, CommentAdded
- Preparado para filas no futuro

## Requisitos nao funcionais
Performance:
- Paginacao
- Indices explicitos
- Evitar N+1
- Performance budget

Seguranca:
- Validacao rigorosa
- Authorization policies
- Rate limiting anti-abuse
- Protecao IDOR
- Logs estruturados

Observability:
- Correlation ID
- Logs
- Healthcheck
- Metricas basicas

Idempotencia:
- Votos
- Race conditions controladas

Tech limits:
- Max comment depth
- Max post/comment length
- Page size limites

## Maturidade senior+
- Feature flags via config/env para ranking/cache
- Governanca: trunk-based dev, PRs pequenos, checklist de PR
- ADR-0001 (stack), ADR-0002 (nested comments), ADR-0003 (ranking)
- Threat modeling: IDOR / XSS / CSRF / brute force / spam + mitigacoes
- CI/CD: lint, tests, security scan, dependency scan
- Tests: unit, feature, integration

## Design system e frontend
- Consistencia visual com Figma
- Componentes reutilizaveis (Button, Card, Badge, Input)
- Tokens de design (cores, tipografia, spacing)
- Estados explicitos (loading, empty, error)
- Acessibilidade minima
- Backend-frontend contract alignment
- Estrutura frontend escalavel

## Roadmap
Milestones M1..M6 com tarefas pequenas, criterios de aceite, riscos/tradeoffs e entregas incrementais.
Detalhes em `docs/roadmap.md`.

## Backlog futuro
- Cache avancado
- Filas/notificacoes
- Moderacao
- Sistema de denuncia
- Evolucao de ranking
- Escalabilidade futura

## Saida esperada
- Especificacoes completas
- Docs em `/docs`
- ADRs em `/docs/adr`
- README forte (objetivos, decisoes, trade-offs, NFRs)
- Checklist de PR
- Plano de execucao claro
- Confirmacao explicita de conexao com Figma antes dos refinamentos finais

## Status atual
- Conexao com Figma bem-sucedida via MCP (figma-mcp).
- Frames coletados: Home (3921:2477), Community (3921:2424), Post (3921:2380, 3921:3947, 3921:17818), Typography (4043:13450).
- Paleta de cores e elevacoes fornecidas por screenshots (Dark/Light + Surface/Elevation). Valores hex nao transcritos.

## Progresso registrado
- MCP do Figma configurado no OpenCode (`C:\Users\Guilherme\.config\opencode\opencode.json`) usando `scripts/figma-mcp.mjs`.
- Token alinhado para `.env` no README.
- `docs/spec.md` atualizado com requisitos guiados por UI e regras de negocio.
- `docs/design-system.md` atualizado com tipografia, componentes e estrutura de layout.
- `docs/roadmap.md` alinhado com entregas de UI (Home e Post).

## Onde paramos
- Falta transcrever os valores HEX dos tokens de cor (Dark/Light) e dos niveis de Surface/Elevation.
- Proximo passo: coletar os HEX (via MCP ou manual) e atualizar `docs/design-system.md`.
