CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE employee
(
    id       UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    age      INTEGER NOT NULL,
    name     TEXT    NOT NULL,
    position TEXT    NOT NULL,
    salary   INTEGER
);