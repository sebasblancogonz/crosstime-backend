CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
DROP TABLE users;
CREATE TABLE users (
    id UUID DEFAULT Uuid_generate_v4 (),
    username VARCHAR(50),
    email varchar(120),
    password varchar(320)
);