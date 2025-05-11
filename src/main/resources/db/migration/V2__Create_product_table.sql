CREATE TABLE IF NOT EXISTS ecommerce.products
(
    id serial NOT NULL,
    name text NOT NULL,
    description text,
    price double precision,
    created_at timestamp without time zone NOT NULL DEFAULT NOW(),
    last_update timestamp without time zone,
    PRIMARY KEY (id)
);