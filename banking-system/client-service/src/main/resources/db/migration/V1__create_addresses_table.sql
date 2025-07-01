CREATE TABLE addresses (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    postal_code VARCHAR(10),
    region VARCHAR(100),
    city VARCHAR(100),
    street VARCHAR(100),
    house VARCHAR(20),
    apartment VARCHAR(10)
);