# We Commerce — Backend API

> Spring Boot 3 / Java 21 / PostgreSQL / JWT — REST API for a multi-vendor e-commerce marketplace.

**Frontend repo**: https://github.com/Hen-Heang/we-commerce-frontend

---

## Quick start

```powershell
# 1. PostgreSQL must be running on localhost:5432
psql -U postgres -c "CREATE DATABASE we_commerce;"

# 2. Set required env vars (or use IntelliJ's run config at .run/)
$env:DB_PASSWORD="123"
$env:JWT_SECRET_KEY="404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970"

# 3. Run
.\gradlew.bat bootRun
# → http://localhost:8080
```

Open Swagger UI: `http://localhost:8080/swagger-ui.html`

---

## What's in here

- 11 REST controllers (auth, users, products, categories, bookmarks, collections, purchases, addresses, notifications, files, view counts)
- 18 JPA entities, 16 repositories
- JWT auth via `OncePerRequestFilter` + DB-tracked tokens (revoke-on-logout)
- Spring Security with role-based access
- Uniform response envelope: `{ payload, message, code, error }`
- Auditing (`@EnableJpaAuditing`) for created/modified timestamps
- CORS configured per-request to support the Next.js frontend

---

## Tech stack

| Concern | Tool |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 3.4 |
| Security | Spring Security + jjwt 0.11 |
| Persistence | Spring Data JPA + Hibernate 6.6 |
| Database | PostgreSQL 16 |
| Build | Gradle |
| API docs | springdoc OpenAPI 2.7 |
| Lombok, MapStruct, ModelMapper | DTO mapping helpers |

---

## Full documentation

📖 See **[../we-commerce-frontend/PROJECT_GUIDE.md](https://github.com/Hen-Heang/we-commerce-frontend/blob/main/PROJECT_GUIDE.md)** for the complete architecture & API reference: endpoint tables, request lifecycle diagrams, auth flow, and end-to-end use cases.

---

## Environment

Secrets are read from env vars (see `.env.example`). Never commit real values.

```
DB_URL=jdbc:postgresql://localhost:5432/we_commerce
DB_USERNAME=postgres
DB_PASSWORD=<your_local_password>
JWT_SECRET_KEY=<64-char hex>
JWT_EXPIRATION=3153600000000
JWT_REFRESH_EXPIRATION=31536000000000
```

For IntelliJ, the `.run/WeCommerceApiApplication.run.xml` config (gitignored) sets these for local dev.

---

## Endpoint overview

All routes are prefixed with `/api/v1`.

| Group | Path | Auth |
|---|---|---|
| Auth | `/auth/register`, `/auth/loginPhoneNumber/{phone}`, `/auth/refresh-token` | public |
| Users | `/user/userprofile`, `/user/edit`, `DELETE /user` | USER |
| Products | `/item/all`, `/item/{id}`, `/item/popular`, `/item/title/{title}`, `/item/categoryName/{name}`, `/item/postProduct` | USER |
| Categories | `/category/all` | USER |
| Bookmarks | `/bookmark/saved`, `/bookmark/unsaved`, `/bookmark/allSaved/{title}` | USER |
| Collections | `/collection/addCollection`, `/collection/allCollection`, `/collection/addBookMark/{id}`, `PUT/DELETE /collection/{id}` | USER |
| Addresses | `/address/addAddressDelivery`, `/address/listAddressDelivery`, `/address/editAddressDelivery/{id}` | USER |
| Purchases | `POST /purchase`, `/purchase/receipt/{id}` | USER |
| Notifications | `/notification/*` | USER |
| Files | `/api/v1/fileView/**` (public), upload routes (USER) | mixed |

Full request/response shapes are in [PROJECT_GUIDE.md](https://github.com/Hen-Heang/we-commerce-frontend/blob/main/PROJECT_GUIDE.md).

---

## Built by

[Hen Heang](https://github.com/Hen-Heang) — backend extended on a starter Spring Boot API to demonstrate JWT auth patterns, layered architecture, and a uniform response envelope.
