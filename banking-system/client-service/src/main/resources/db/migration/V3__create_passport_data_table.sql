CREATE TABLE passport_data (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    series VARCHAR(10),
    number VARCHAR(20),
    issue_date DATE,
    issuer VARCHAR(200),
    code VARCHAR(20)
);