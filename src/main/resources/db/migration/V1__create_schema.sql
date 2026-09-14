-- The schema the entities map to. Storm validates every entity against it at startup, so a column
-- that is added here and not in the entity (or the other way round) fails the application fast.
-- This file is also what the schema validation test applies, so it stays valid on H2 and PostgreSQL.

CREATE TABLE city (
    id   INTEGER      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE app_user (
    id          INTEGER      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    email       VARCHAR(255) NOT NULL UNIQUE,
    birth_date  DATE,
    street      VARCHAR(255) NOT NULL,
    postal_code VARCHAR(16)  NOT NULL,
    city_id     INTEGER      NOT NULL REFERENCES city (id)
);

CREATE INDEX idx_app_user_city ON app_user (city_id);
