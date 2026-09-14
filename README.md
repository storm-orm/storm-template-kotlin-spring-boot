# Storm application template · Kotlin + Spring Boot

A minimal, runnable Spring Boot application on [Storm](https://orm.st): two entities, one repository
query, one service, two endpoints and the tests that cover them. It is a starting point, not a demo —
there is nothing here to delete before writing your own code.

```bash
./gradlew bootRun      # http://localhost:8080
./gradlew test
```

Both commands work on a fresh clone: the application runs on an H2 database in `./data`, created by
the Flyway migration on first start, and the tests run on H2 of their own.

## What is here

```
src/main/kotlin/com/example/app/
├── Application.kt         the Spring Boot entry point; the starter does the Storm wiring
├── model/                  City and User, immutable data classes with @PK and @FK
├── repository/             EntityRepository interfaces; Storm implements and registers them
├── service/                UserService, where the transactions are
└── web/                    the controller and the request and response types
src/main/resources/
├── application.yaml        the H2 datasource; application-postgres.yaml is the alternative
└── db/migration/           V1 creates the schema, V2 seeds two rows
src/test/kotlin/com/example/app/
├── UserRepositoryTest.kt           @StormTest on H2, asserting the SQL through SqlCapture
└── EntitySchemaValidationTest.kt   every entity checked against the migration
```

The starter builds the `ORMTemplate` from the `DataSource`, registers every repository interface as a
bean, bridges Storm's transactions with Spring's, and validates every entity against the live schema
at startup, after Flyway has migrated it.

## The vertical slice

`GET /users?city=Amsterdam` reads users and their city in one statement; the metamodel path
`User_.city.name` navigates the foreign key, so the query never names the join.

`POST /users` registers a user, creating the city on first use. Both writes run in one
`@Transactional` method, so a failure leaves neither behind.

```bash
curl 'http://localhost:8080/users?city=Amsterdam'
curl -X POST http://localhost:8080/users -H 'Content-Type: application/json' \
  -d '{"email":"grace@example.com","street":"Oudegracht 3","postalCode":"3511 AA","city":"Utrecht"}'
```

## Making it yours

1. Rename the `com.example.app` package and the `rootProject.name` in `settings.gradle.kts`.
2. Replace `V1__create_schema.sql` with your schema and the entities with your tables. Storm
   validates the two against each other at startup, so a mismatch fails the application rather than
   the first request that hits it.
3. Delete `V2__sample_data.sql`.

## PostgreSQL

The application runs on H2 so that a fresh clone needs nothing installed. To use PostgreSQL instead:

```bash
docker compose up -d
./gradlew bootRun --args='--spring.profiles.active=postgres'
```

Both dialect modules and both drivers are already on the runtime classpath, so nothing else changes.

## Where to go next

- [Storm documentation](https://orm.st/docs) — entities, queries, transactions, refs
- [Example applications](https://orm.st/examples/) — the same ideas in a full application, with
  pagination, projections, observability and a UI
