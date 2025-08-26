CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE users
(
    id         UUID         NOT NULL DEFAULT gen_random_uuid(),
    username   VARCHAR(50)  NOT NULL,
    password   VARCHAR(255) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT uc_users_username UNIQUE (username);