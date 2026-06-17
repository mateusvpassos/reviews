# Reviews — CRUD com Spring Boot

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.5.5-brightgreen)
![Spring Security](https://img.shields.io/badge/Spring%20Security-green)
![Flyway](https://img.shields.io/badge/Flyway-red)
![H2](https://img.shields.io/badge/H2-blue)

API REST de CRUD de usuários, assuntos e avaliações (reviews), com autenticação por filtro customizado, validação por anotações, migrations Flyway e tratamento de exceções padronizado. Projeto de estudo mais completo do conjunto.

## Stack

- Java 17, Spring Boot 2.5.5
- Spring Web, Spring Security (filtro de autenticação custom)
- Spring Data JPA, Flyway
- H2 (perfil padrão); pronto para outros bancos
- Bean Validation + validators customizados (`@AtLeastZero`, `@GreaterThanZero`)
- Guava, Lombok

## Como rodar

Perfil `h2` ativo por padrão — sem dependências externas:

```bash
./mvnw spring-boot:run
```

App em `http://localhost:8080`. Console H2 (se habilitado): `http://localhost:8080/h2-console`.

## Endpoints

| Método | Rota                | Descrição           |
|--------|---------------------|---------------------|
| GET    | `/users`            | Lista usuários      |
| GET    | `/users/{sourceId}` | Busca usuário       |
| DELETE | `/users/{sourceId}` | Remove usuário      |
| GET    | `/subjects`         | Lista assuntos      |
| GET    | `/reviews`          | Lista avaliações    |

(CRUD completo por entidade; ver controllers para POST/PUT.)

## Estrutura

```
src/main/java/br/com/mateus/crud/endpoint
├── annotation/  + validator/   # validações customizadas
├── config/                     # App e Security
├── controller/                 # User, Subject, Review
├── domain/  + dto/             # entidades e DTOs
├── exception/                  # exists/, notFound/, handler/
├── filter/                     # AuthenticationFilter
├── repository/  + service/
└── thread/  + util/

src/main/resources/db/migration # scripts Flyway (V1..Vn)
```
