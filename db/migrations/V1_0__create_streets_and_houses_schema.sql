CREATE TABLE IF NOT EXISTS streets (
    id bigserial NOT NULL,
    name varchar,
    index integer,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS houses (
    id bigserial NOT NULL,
    name varchar,
    date_of_constr date,
    number_of_floors integer,
    type varchar(15) CHECK (type IN ('LivingQuarter', 'CommercialSpace', 'Garage', 'UtilityRoom')),
    street bigint,
    PRIMARY KEY (id),
    FOREIGN KEY (street) REFERENCES streets(id)
);
