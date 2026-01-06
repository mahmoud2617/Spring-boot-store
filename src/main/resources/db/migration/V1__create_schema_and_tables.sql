CREATE SCHEMA IF NOT EXISTS practice_schema;

CREATE TABLE practice_schema.users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE practice_schema.profiles (
    id BIGSERIAL PRIMARY KEY,
    bio TEXT,
    phone_number VARCHAR(15),
    date_of_birth DATE,
    loyalty_points INT DEFAULT 0,

    CONSTRAINT fk_profile_user
    FOREIGN KEY (id) REFERENCES users(id),
    CONSTRAINT check_positive CHECK (loyalty_points >= 0)
);

CREATE TABLE practice_schema.addresses (
    id BIGSERIAL PRIMARY KEY,
    street VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    zip VARCHAR(255) NOT NULL,
    state VARCHAR(255) NOT NULL,
    user_id BIGINT,

    CONSTRAINT fk_address_user
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE practice_schema.categories (
    id SMALLSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE practice_schema.products (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    category_id SMALLINT,

    CONSTRAINT fk_product_category FOREIGN KEY (category_id) REFERENCES categories(id)
);