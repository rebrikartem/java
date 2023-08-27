ALTER TABLE IF EXISTS houses
ADD COLUMN IF NOT EXISTS material varchar;


CREATE TABLE IF NOT EXISTS apartments (
    id bigserial,
    number integer,
    area integer,
    number_of_rooms integer,
    house bigint,
    PRIMARY KEY (id),
    FOREIGN KEY (house) REFERENCES houses(id)
);
