-- Two rows so the endpoints answer something on a fresh database. Delete this file when the
-- application has data of its own.

INSERT INTO city (name) VALUES ('Amsterdam'), ('Rotterdam');

INSERT INTO app_user (email, birth_date, street, postal_code, city_id)
VALUES ('ada@example.com', '1815-12-10', 'Keizersgracht 1', '1015 CJ', 1),
       ('alan@example.com', '1912-06-23', 'Coolsingel 2', '3011 AD', 2);
