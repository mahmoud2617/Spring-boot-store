CREATE TABLE practice_schema.orders (
    id BIGSERIAL PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    total_price DECIMAL(10, 2),

    CONSTRAINT orders_users_id_fk
        FOREIGN KEY (customer_id) REFERENCES practice_schema.users (id)
);

CREATE TABLE practice_schema.order_items (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    unit_price DECIMAL(10, 2) NOT NULL,
    quantity INT NOT NULL,
    total_price DECIMAL(10, 2) NOT NULL,

    CONSTRAINT order_items_orders_fk
        FOREIGN KEY (order_id) REFERENCES practice_schema.orders (id),

    CONSTRAINT order_items_products_fk
        FOREIGN KEY (product_id) REFERENCES practice_schema.products (id)
);