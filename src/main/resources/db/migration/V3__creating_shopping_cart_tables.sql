CREATE TABLE practice_schema.carts (
    id UUID PRIMARY KEY,
    date_created DATE DEFAULT CURRENT_DATE
);

CREATE TABLE practice_schema.cart_item (
    id BIGSERIAL PRIMARY KEY,
    cart_id UUID NOT NULL,
    product_id BIGINT NOT NULL,
    quantity SMALLINT NOT NULL DEFAULT 1,

    CONSTRAINT cart_id_product_id_unique
    UNIQUE (cart_id, product_id),

    CONSTRAINT cart_item_carts_fk
    FOREIGN KEY (cart_id) REFERENCES practice_schema.carts (id)
    ON DELETE CASCADE,

    CONSTRAINT cart_item_products_fk
    FOREIGN KEY (product_id) REFERENCES practice_schema.products (id)
    ON DELETE CASCADE
);