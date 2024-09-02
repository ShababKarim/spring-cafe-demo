CREATE TABLE IF NOT EXISTS menu_item (
    id SERIAL PRIMARY KEY,
    name TEXT,
    ingredients TEXT[],
    quantity BIGINT,
    price MONEY
);

CREATE TABLE IF NOT EXISTS "order" (
    id SERIAL PRIMARY KEY,
    customer_name TEXT,
    price BIGINT,
    drinks TEXT,
    updated_on TIMESTAMP
);
