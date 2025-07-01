INSERT INTO addresses (postal_code, region, city, street, house, apartment) VALUES
    ('123456', 'Moscow Region', 'Moscow', 'Lenina', '10', '5'),
    ('654321', 'Saint Petersburg', 'Saint Petersburg', 'Nevsky', '25', NULL),
    ('789012', 'Novosibirsk Region', 'Novosibirsk', 'Sovetskaya', '15', '3'),
    ('345678', 'Kazan Region', 'Kazan', 'Bauman', '7', '12'),
    ('901234', 'Ekaterinburg', 'Ekaterinburg', 'Lenina', '30', NULL),
    ('567890', 'Rostov Region', 'Rostov-on-Don', 'Pushkinskaya', '8', '4'),
    ('432109', 'Krasnodar Region', 'Krasnodar', 'Krasnaya', '20', '9'),
    ('876543', 'Nizhny Novgorod', 'Nizhny Novgorod', 'Bolshaya Pokrovskaya', '12', NULL),
    ('210987', 'Samara Region', 'Samara', 'Galaktionovskaya', '5', '6'),
    ('654321', 'Chelyabinsk Region', 'Chelyabinsk', 'Truda', '18', '2');

INSERT INTO contact_info (email, phone_number) VALUES
    ('user1@example.com', '+79991234567'),
    ('user2@example.com', '+78121234567'),
    ('user3@example.com', '+73832345678'),
    ('user4@example.com', '+78432234567'),
    ('user5@example.com', '+73431234567'),
    ('user6@example.com', '+78631234567'),
    ('user7@example.com', '+78612345678'),
    ('user8@example.com', '+78311234567'),
    ('user9@example.com', '+78451234567'),
    ('user10@example.com', '+73561234567');

INSERT INTO passport_data (series, number, issue_date, issuer, code) VALUES
    ('1234', '567890', '2020-01-15', 'MVD Moscow', '770-001'),
    ('4321', '098765', '2019-06-20', 'MVD SPB', '780-002'),
    ('5678', '123456', '2021-03-10', 'MVD Novosibirsk', '540-003'),
    ('9012', '345678', '2018-09-05', 'MVD Kazan', '160-004'),
    ('3456', '789012', '2022-07-12', 'MVD Ekaterinburg', '660-005'),
    ('6789', '234567', '2020-11-25', 'MVD Rostov', '610-006'),
    ('2345', '876543', '2019-04-18', 'MVD Krasnodar', '230-007'),
    ('8765', '432109', '2021-08-30', 'MVD Nizhny Novgorod', '520-008'),
    ('5432', '109876', '2020-12-15', 'MVD Samara', '630-009'),
    ('9876', '654321', '2019-10-01', 'MVD Chelyabinsk', '740-010');

INSERT INTO clients (first_name, last_name, middle_name, birth_date, password, status, registration_date, passport_data_id, contact_info_id, address_id) VALUES
    ('Ivan', 'Petrov', 'Sergeevich', '1990-05-12', 'password123', 'ACTIVE', '2023-01-01 10:00:00', 1, 1, 1),
    ('Maria', 'Ivanova', 'Alexandrovna', '1995-08-23', 'pass456', 'ACTIVE', '2023-02-15 14:30:00', 2, 2, 2),
    ('Alexey', 'Smirnov', 'Dmitrievich', '1988-03-15', 'pass789', 'ACTIVE', '2023-03-10 09:15:00', 3, 3, 3),
    ('Elena', 'Kuznetsova', 'Victorovna', '1992-11-20', 'pass321', 'SUSPENDED', '2023-04-05 13:45:00', 4, 4, 4),
    ('Dmitry', 'Morozov', 'Alexandrovich', '1985-07-07', 'pass654', 'ACTIVE', '2023-05-12 11:30:00', 5, 5, 5),
    ('Olga', 'Volkova', 'Sergeevna', '1998-09-30', 'pass987', 'ACTIVE', '2023-06-20 16:00:00', 6, 6, 6),
    ('Sergey', 'Lebedev', 'Ivanovich', '1983-04-25', 'pass147', 'SUSPENDED', '2023-07-01 10:00:00', 7, 7, 7),
    ('Natalia', 'Fedorova', 'Alexandrovna', '1991-12-10', 'pass258', 'ACTIVE', '2023-08-15 14:20:00', 8, 8, 8),
    ('Andrey', 'Pavlov', 'Victorovich', '1987-06-18', 'pass369', 'ACTIVE', '2023-09-10 09:10:00', 9, 9, 9),
    ('Anna', 'Sokolova', 'Dmitrievna', '1994-02-14', 'pass741', 'SUSPENDED', '2023-10-05 15:30:00', 10, 10, 10);

INSERT INTO clients_change_history (field_name, old_value, new_value, change_date, client_id) VALUES
    ('status', 'SUSPENDED', 'ACTIVE', '2023-01-02 12:00:00', 1),
    ('email', 'newuser0@example.com', 'newuser1@example.com', '2023-03-01 09:00:00', 1),
    ('status', 'SUSPENDED', 'ACTIVE', '2023-02-16 14:00:00', 2),
    ('phone_number', '+78121234567', '+78129876543', '2023-04-01 10:30:00', 2),
    ('status', 'SUSPENDED', 'ACTIVE', '2023-03-11 09:30:00', 3),
    ('password', 'pass789', 'newpass789', '2023-05-01 12:00:00', 3),
    ('status', 'ACTIVE', 'SUSPENDED', '2023-04-06 14:00:00', 4),
    ('email', 'user4@example.com', 'newuser4@example.com', '2023-06-01 11:00:00', 4),
    ('status', 'SUSPENDED', 'ACTIVE', '2023-05-13 12:00:00', 5),
    ('password', 'pass654', 'newpass654', '2023-07-01 10:00:00', 5),
    ('status', 'SUSPENDED', 'ACTIVE', '2023-06-21 16:30:00', 6),
    ('email', 'user6@example.com', 'newuser6@example.com', '2023-08-01 09:00:00', 6),
    ('status', 'ACTIVE', 'SUSPENDED', '2023-07-02 10:30:00', 7),
    ('phone_number', '+78612345678', '+78619876543', '2023-09-01 14:00:00', 7),
    ('status', 'SUSPENDED', 'ACTIVE', '2023-08-16 14:50:00', 8),
    ('password', 'pass258', 'newpass258', '2023-10-01 11:00:00', 8),
    ('status', 'SUSPENDED', 'ACTIVE', '2023-09-11 09:40:00', 9),
    ('email', 'user9@example.com', 'newuser9@example.com', '2023-11-01 10:00:00', 9),
    ('status', 'ACTIVE', 'SUSPENDED', '2023-10-06 16:00:00', 10),
    ('password', 'pass741', 'newpass741', '2023-12-01 12:00:00', 10);