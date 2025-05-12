CREATE TABLE IF NOT EXISTS ecommerce.photos
(
    photo_id text NOT NULL,
    name text NOT NULL,
    extension text,
    content bytea NOT NULL,
    product_id serial,
    is_main_photo boolean NOT NULL DEFAULT false,
    PRIMARY KEY (photo_id),
    CONSTRAINT fk_product_id FOREIGN KEY (product_id)
        REFERENCES ecommerce.products (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT product_id_and_main_photo_unique UNIQUE (product_id, is_main_photo)
);