CREATE TABLE IF NOT EXISTS users (
    id bigserial not null,
    email varchar(255) UNIQUE,
    firstname varchar(255),
    lastname varchar(255),
    password varchar(255),
    role varchar(5) CHECK (role IN ('ADMIN', 'USER')),
    street bigint,
    PRIMARY KEY (id),
    FOREIGN KEY (street) REFERENCES streets(id)
);

ALTER TABLE IF EXISTS streets
ADD COLUMN IF NOT EXISTS users bigint;
