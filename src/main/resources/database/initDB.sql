DROP TABLE IF EXISTS cars;
DROP SEQUENCE IF EXISTS cars_id_seq;

CREATE TABLE cars (
    id  BIGSERIAL NOT NULL PRIMARY KEY,
    brand VARCHAR (20) NOT NULL,
    model VARCHAR (40) NOT NULL,
    year integer NOT NULL,
    month integer NOT NULL,
    engine_displacement integer NOT NULL,
    turbo boolean NOT NULL,
    power integer NOT NULL,
    transmission_type VARCHAR (10) NOT NULL,
    drive_type VARCHAR (10) NOT NULL,
    body_type VARCHAR (10) NOT NULL,
    color VARCHAR (40) NOT NULL
);