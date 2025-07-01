CREATE TABLE clients (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    middle_name VARCHAR(255),
    birth_date DATE,
    password VARCHAR(255),
    status VARCHAR(20),
    registration_date TIMESTAMP WITHOUT TIME ZONE,
    passport_data_id BIGINT UNIQUE,
    contact_info_id BIGINT UNIQUE,
    address_id BIGINT,
    CONSTRAINT fk_passport_data FOREIGN KEY (passport_data_id) REFERENCES passport_data(id),
    CONSTRAINT fk_contact_info FOREIGN KEY (contact_info_id) REFERENCES contact_info(id),
    CONSTRAINT fk_address FOREIGN KEY (address_id) REFERENCES addresses(id)
);