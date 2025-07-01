INSERT INTO deposit_type (name) VALUES
    ('Standard Savings'),
    ('Fixed-Term Deposit'),
    ('Pension Plan'),
    ('Children’s Deposit'),
    ('Business Account'),
    ('High-Interest Savings'),
    ('Flexible Deposit'),
    ('Gold Deposit'),
    ('Silver Deposit'),
    ('VIP Savings');

INSERT INTO deposit_conditions (deposit_type_id, is_top_up_allowed, is_partial_withdrawal_allowed, minimum_balance, interest_payment_frequency) VALUES
    (1, true, false, 1000.00, 'MONTHLY'),
    (2, false, false, 5000.00, 'ANNUALLY'),
    (3, true, true, 2000.00, 'QUARTERLY'),
    (4, true, false, 500.00, 'MONTHLY'),
    (5, true, true, 10000.00, 'MONTHLY'),
    (6, false, false, 3000.00, 'ANNUALLY'),
    (7, true, true, 1500.00, 'MONTHLY'),
    (8, false, false, 10000.00, 'QUARTERLY'),
    (9, true, false, 2000.00, 'ANNUALLY'),
    (10, false, false, 50000.00, 'MONTHLY');

INSERT INTO deposits (client_id, contract_number, deposit_type_id, contract_amount, interest_rate, contract_term, conclusion_date, end_date, status) VALUES
    (1, 'CNTR-001-2023', 1, 15000.00, 3.5, 12, '2023-01-15', '2024-01-15', 'ACTIVE'),
    (2, 'CNTR-002-2023', 2, 25000.00, 4.2, 24, '2023-02-20', '2025-02-20', 'ACTIVE'),
    (3, 'CNTR-003-2023', 3, 18000.00, 3.0, 36, '2023-03-10', '2026-03-10', 'SUSPENDED'),
    (4, 'CNTR-004-2023', 4, 5000.00, 2.8, 12, '2023-04-05', '2024-04-05', 'ACTIVE'),
    (5, 'CNTR-005-2023', 5, 30000.00, 4.5, 18, '2023-05-12', '2024-11-12', 'ACTIVE'),
    (6, 'CNTR-006-2023', 6, 20000.00, 4.0, 24, '2023-06-20', '2025-06-20', 'ACTIVE'),
    (7, 'CNTR-007-2023', 7, 12000.00, 3.2, 12, '2023-07-01', '2024-07-01', 'SUSPENDED'),
    (8, 'CNTR-008-2023', 8, 50000.00, 5.0, 36, '2023-08-15', '2026-08-15', 'ACTIVE'),
    (9, 'CNTR-009-2023', 9, 15000.00, 3.8, 18, '2023-09-10', '2025-03-10', 'ACTIVE'),
(10, 'CNTR-010-2023', 10, 75000.00, 5.5, 24, '2023-10-05', '2025-10-05', 'SUSPENDED');

INSERT INTO operations (deposit_id, operation_type, amount, operation_date) VALUES
    (1, 'DEPOSIT', 15000.00, '2023-01-15 10:00:00'),
    (2, 'DEPOSIT', 25000.00, '2023-02-20 14:30:00'),
    (3, 'DEPOSIT', 18000.00, '2023-03-10 09:15:00'),
    (4, 'DEPOSIT', 5000.00, '2023-04-05 13:45:00'),
    (5, 'DEPOSIT', 30000.00, '2023-05-12 11:30:00'),
    (6, 'INTEREST_PAYMENT', 200.00, '2023-06-20 16:00:00'),
    (7, 'DEPOSIT', 12000.00, '2023-07-01 10:00:00'),
    (8, 'DEPOSIT', 50000.00, '2023-08-15 14:20:00'),
    (9, 'INTEREST_PAYMENT', 150.00, '2023-09-10 09:10:00'),
    (10, 'DEPOSIT', 75000.00, '2023-10-05 15:30:00');