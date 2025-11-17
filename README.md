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
