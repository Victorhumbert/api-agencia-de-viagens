# API Agência de Viagens (Spring Boot)

Projeto simples em Java + Spring Boot que expõe endpoints REST para gerenciar destinos de viagem.

Endpoints principais
- `POST /api/destinations` : Criar novo destino. Body: `{ "name": "...", "location": "...", "description": "...", "availablePackages": 5 }`
- `GET /api/destinations` : Listar destinos. Query params opcionais: `name`, `location` para buscar.
- `GET /api/destinations/{id}` : Ver detalhes do destino.
- `PUT /api/destinations/{id}` : Atualizar (parcialmente) campos do destino. Body: `DestinationRequest`.
- `PATCH /api/destinations/{id}/rating?score=7` : Enviar avaliação (1-10) — atualiza média.
- `POST /api/destinations/{id}/reservations` : Reservar pacote(s). Body: `{ "customerName": "...", "quantity": 1 }`
- `DELETE /api/destinations/{id}` : Excluir destino.

Rodando o projeto

Requisitos: Java 17 e Maven.

No diretório do projeto rode:

```bash
mvn spring-boot:run
```

Rodando com Docker / Docker Compose

Pré-requisitos: Docker e Docker Compose instalados.

1. Construir e subir os serviços (Postgres + app):

```bash
docker compose up --build
```

Isso vai:
- subir um container `db` (Postgres) e um container `app` (sua aplicação Spring Boot construída a partir do `Dockerfile`).
- a aplicação usará as variáveis de ambiente configuradas em `docker-compose.yml` para conectar no banco.

2. Parar os serviços:

```bash
docker compose down
```

3. Logs úteis (em outro terminal):

```bash
docker compose logs -f app
docker compose logs -f db
```

Credenciais e usuários iniciais
- Postgres: `postgres` / `123123` (conforme `docker-compose.yml`).
- Usuários de aplicação (seed): `admin` / `admin` (ROLE_ADMIN + ROLE_USER) e `user` / `password` (ROLE_USER).

Testes rápidos (após a aplicação subir):

Listar destinos (usuário):
```bash
curl -u user:password http://localhost:8080/api/destinations
```

Criar destino (admin):
```bash
curl -u admin:admin -H "Content-Type: application/json" -d '{"name":"Praia X","location":"Região Y","description":"...","availablePackages":10}' -X POST http://localhost:8080/api/destinations
```


Teste via curl (exemplos)

Criar destino:
```bash
curl -X POST -H "Content-Type: application/json" -d '{"name":"Praia Azul","location":"Bahia","description":"Resort all-inclusive","availablePackages":10}' http://localhost:8080/api/destinations
```

Listar destinos:
```bash
curl http://localhost:8080/api/destinations
```

Avaliar destino:
```bash
curl -X PATCH http://localhost:8080/api/destinations/1/rating?score=9
```

Reservar pacote:
```bash
curl -X POST -H "Content-Type: application/json" -d '{"customerName":"Joao","quantity":2}' http://localhost:8080/api/destinations/1/reservations
```
