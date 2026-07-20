# Project Context

Facts below are confirmed from the current repository (`build.gradle`, `application.yml`, `config/`, `controller/`, `entity/`). Treat this file, not memory or older docs, as the description of what exists today.

## Purpose

`we-commerce-api` is the Spring Boot backend for a multi-vendor e-commerce marketplace. It serves a separate Next.js frontend (`we-commerce-frontend`), but **that repository is out of scope here** — do not read, reference paths in, or assume behavior from it. If frontend compatibility matters for a change, ask the user rather than inspecting a sibling repo.

## Major domains

Auth/users, products, categories, bookmarks, collections, delivery addresses, purchases/receipts, notifications, file upload/view, product view counts.

## Confirmed stack

- Java 21 (Gradle toolchain)
- Spring Boot 3.4.3, Spring Web, Spring Security (`@EnableMethodSecurity`)
- Spring Data JPA + Hibernate 6.x, `database-platform: PostgreSQLDialect`
- PostgreSQL (driver `org.postgresql`)
- JWT via `io.jsonwebtoken:jjwt` 0.11.5 — a hand-rolled `JwtService`/`JwtAuthenticationFilter`, **not** Spring Authorization Server or OAuth2 resource server
- Lombok
- MapStruct 1.5.5.Final **and** ModelMapper 3.1.1 — both are real dependencies; check which one a given service actually uses before adding a new mapping, they are not interchangeable everywhere
- springdoc-openapi-starter-webmvc-ui 2.7.0 (Swagger UI at `/swagger-ui.html`)
- JUnit 5 (`useJUnitPlatform()`), `spring-security-test`
- Gradle only — no Maven files exist, do not suggest `mvn` commands

Not present unless the user explicitly asks for it as a migration: MyBatis, Thymeleaf, Redis, Kafka/RabbitMQ, Spring Cloud, WebFlux.

## Request lifecycle

```text
HTTP Request
    ↓
JwtAuthenticationFilter (OncePerRequestFilter)
    ↓
Spring Security authorizeHttpRequests rules (SecurityConfiguration)
    ↓
Controller (/api/v1/**)
    ↓
Service (interface + impl per feature, service/<Feature>/)
    ↓
Repository (Spring Data JPA, repository/<Feature>/)
    ↓
PostgreSQL
    ↓
Response DTO, wrapped by response/ApiResponse.java
```

There is no separate admin request flow — role-based access is coarse-grained (`ROLE_USER` vs public), not admin/customer split.

## Package structure (`com.example.wecommerce_api`, layered not feature-sliced)

- `controller/` — 11 REST controllers, all routes prefixed `/api/v1` (Auth, User, Product, Category, Bookmark, Collection, Address, Purchase, Notification, File, ProductViewCount)
- `service/<Feature>/` — interface + impl per feature
- `repository/<Feature>/` — Spring Data JPA repositories, grouped per entity
- `entity/` — 18 JPA entities, including `entity/token/Token.java` for DB-tracked JWTs
- `payload/<Feature>/` — request/response DTOs, grouped by feature not by controller
- `response/ApiResponse.java` — the intended single envelope: `{ payload, message, code, error, date }`
- `response/ProductResponse.java` — a second, differently-shaped paginated envelope used for product listing responses
- `exception/` — one handler class per error type (`NotFoundExceptionHandler`, `DuplicateFieldExceptionHandler`, `UserDuplicateExceptionHandler`, `FieldEmptyExceptionHandler`, `InvalidValueExceptionHandler`, `BadRequestException`, `CustomExceptionSecurity`) plus `GlobalExceptionHandler`/`GlobalExceptionHandlerSecurity` as `@ControllerAdvice` entry points; shared validation lives in `exception/exceptionValidateInput/Validation.java`
- `config/` — `SecurityConfiguration`, `JwtAuthenticationFilter`, `JwtService`, `LogoutService`, `CorsFilterConfiguration`, `AuditorAwareImpl` (feeds `@EnableJpaAuditing`), `OpenApiConfig`, `AppConfig`
- `enums/` — `Role`, `Permission`, `TokenType`, `IResponseMessage`/`ResponseMessage`

## Entry points

- `WeCommerceApiApplication.java` — main class
- `SecurityConfiguration.securityFilterChain` — authorization rules
- `JwtAuthenticationFilter` — validates JWTs both cryptographically (`JwtService`) and against `TokenRepository` (DB-tracked revocation/expiry)
- `LogoutService` — revokes the DB-tracked token on `/api/v1/auth/logout`

## Environment variables (names only — see rules/10-boundaries.md, never read or print values)

`DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, `JWT_SECRET_KEY`, `JWT_EXPIRATION`, `JWT_REFRESH_EXPIRATION`, `PORT` (Render sets this; `server.port: ${PORT:8080}`).

## Deployment

Multi-stage `Dockerfile` (Gradle build stage → JRE runtime stage, tests skipped in image build via `-x test`), deployed via `render.yaml` (Render.com, Docker runtime, Singapore region, free plan). DB/JWT env vars are `sync: false` (set manually in Render dashboard, not committed).

## Known architectural inconsistencies (confirmed by reading the code — do not silently "fix" these without a plan and explicit approval)

- **Security matcher gap**: `SecurityConfiguration` lists role-gated prefixes as *exact* paths (`/api/v1/user`, `/api/v1/item`, `/api/v1/purchase`, etc.) with no trailing `/**`. Spring's `AntPathMatcher` treats these as exact matches only, so nested routes like `/api/v1/user/edit` or `/api/v1/item/postProduct` do **not** match those rules and instead fall through to `.anyRequest().authenticated()` — authenticated with *any* role, not specifically `hasRole("USER")`. Flag this whenever touching security or adding a new nested route; do not assume `hasRole("USER")` is enforced on sub-paths just because the top-level prefix appears in the permit/role list.
- **Response envelope is not uniform**: `AuthController` responses and `response/ProductResponse.java` do not use the same shape as `response/ApiResponse.java`. Treat "one consistent envelope" as an aspirational target, not current behavior.
- **Auth code has active in-progress changes**: as of this writing there are uncommitted edits to `AuthController`, `payload/auth/PhoneLoginRequest.java`, `payload/auth/RegisterRequest.java`, and `service/Auth/AuthenticationService.java`, plus a new test `service/Auth/AuthenticationServiceTest.java`. Do not assume prior known gaps (e.g. unauthenticated phone login, unpersisted refresh tokens, client-supplied `role` on register) are still present — re-read the current file before describing auth behavior, and never overwrite these in-progress edits.

## Documentation drift

`README.md` links to `we-commerce-frontend/PROJECT_GUIDE.md` as the full architecture reference — that file lives in the sibling repo and is out of scope for this repo's Claude configuration (see boundaries). Prefer the current code over README claims when they disagree.
