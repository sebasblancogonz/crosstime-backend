CREATE TABLE IF NOT EXISTS users (
    id varbinary(36),
    username varchar(50),
    email varchar(120),
    password varchar(320),
    PRIMARY KEY (id)
);