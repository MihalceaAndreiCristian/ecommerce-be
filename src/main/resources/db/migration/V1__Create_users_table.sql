CREATE TABLE IF NOT EXISTS ecommerce.users
(
    id serial NOT NULL,
    username text NOT NULL,
    password text NOT NULL,
    first_name text,
    last_name text,
    email text,
    role text NOT NULL DEFAULT USER,
    PRIMARY KEY (id),
    CONSTRAINT username_unique UNIQUE (username),
    CONSTRAINT email_unique UNIQUE (email)
);