CREATE EXTENSION IF NOT EXISTS citext;

CREATE TABLE product (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    start_date DATE
);

CREATE TABLE sku (
    id SERIAL PRIMARY KEY,
    product_id INTEGER NOT NULL,
    sku_code VARCHAR(100) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE
);

