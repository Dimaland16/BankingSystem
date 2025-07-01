CREATE TABLE clients_change_history (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    field_name VARCHAR(50),
    old_value VARCHAR(255),
    new_value VARCHAR(255),
    change_date TIMESTAMP WITHOUT TIME ZONE,
    client_id BIGINT NOT NULL,
    CONSTRAINT fk_client FOREIGN KEY (client_id) REFERENCES clients(id)
);