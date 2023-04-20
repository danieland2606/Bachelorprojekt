-- Customer 1
-- Inserting address
INSERT INTO address (city, postal_code, street)
VALUES ('New York', '10001', 'Broadway');

-- Inserting customer
INSERT INTO customer (id, bank_details, date_of_birth, e_mail, employment_status, first_name, form_of_address, last_name, marital_status, phone_number, title, address_id)
VALUES (1, '1234567890', '1990-01-01', 'john.doe@example.com', 'Employed', 'John', 'Mr.', 'Doe', 'Single', '123-456-7890', 'Mr.', 1);

-- Inserting object of insurance
INSERT INTO object_of_insurance (castrated, color, date_of_birth, environment, name, personality, race, weight)
VALUES (1, 'Brown', '2010-01-01', 'Indoor', 'Fluffy', 'Friendly', 'Persian', 3.5);

-- Inserting policy
INSERT INTO policy (coverage, end_date, premium, start_date, customer_id, object_of_insurance_id)
VALUES (100000, '2024-01-01', 1000.00, '2022-01-01', 1, 1);


-- customer 2
-- Inserting address
INSERT INTO address (city, postal_code, street)
VALUES ('Los Angeles', '90001', 'Main St.');

-- Inserting customer
INSERT INTO customer (id, bank_details, date_of_birth, e_mail, employment_status, first_name, form_of_address, last_name, marital_status, phone_number, title, address_id)
VALUES (2, '0987654321', '1985-05-10', 'jane.doe@example.com', 'Unemployed', 'Jane', 'Ms.', 'Doe', 'Married', '987-654-3210', 'Ms.', 2);

-- Inserting object of insurance
INSERT INTO object_of_insurance (castrated, color, date_of_birth, environment, name, personality, race, weight)
VALUES (0, 'Black', '2015-03-15', 'Outdoor', 'Max', 'Energetic', 'Labrador', 5.5);

-- Inserting policy
INSERT INTO policy (coverage, end_date, premium, start_date, customer_id, object_of_insurance_id)
VALUES (50000, '2023-12-31', 500.00, '2021-01-01', 2, 2);





-- Insert customer with id 10 and address id 1
INSERT INTO address (id, city, postal_code, street) VALUES (1, 'Berlin', '10115', 'Alexanderplatz 1');
INSERT INTO customer (id, bank_details, date_of_birth, e_mail, employment_status, first_name, form_of_address, last_name, marital_status, phone_number, title, address_id)
VALUES (10, 'DE12 3456 7890 1234 5678 90', '1980-01-01', 'customer10@example.com', 'Employed', 'John', 'Mr.', 'Doe', 'Married', '+491234567890', NULL, 1);

-- Inserting address
INSERT INTO address (city, postal_code, street)
VALUES ('San Francisco', '94105', 'Market St.');

-- Inserting customer
INSERT INTO customer (id, bank_details, date_of_birth, e_mail, employment_status, first_name, form_of_address, last_name, marital_status, phone_number, title, address_id)
VALUES (10, '9876543210', '1985-03-12', 'jane.smith@example.com', 'Self-Employed', 'Jane', 'Ms.', 'Smith', 'Married', '555-123-4567', 'Ms.', 1);

-- Inserting object of insurance
INSERT INTO object_of_insurance (castrated, color, date_of_birth, environment, name, personality, race, weight)
VALUES (0, 'Black and White', '2017-05-01', 'Indoor', 'Luna', 'Shy', 'Domestic Shorthair', 4.2);

-- Inserting policies
INSERT INTO policy (coverage, end_date, premium, start_date, customer_id, object_of_insurance_id)
VALUES (50000, '2024-05-01', 500.00, '2022-05-01', 10, 1);

INSERT INTO policy (coverage, end_date, premium, start_date, customer_id, object_of_insurance_id)
VALUES (75000, '2024-05-01', 750.00, '2022-05-01', 10, 1);

INSERT INTO policy (coverage, end_date, premium, start_date, customer_id, object_of_insurance_id)
VALUES (100000, '2024-05-01', 1000.00, '2022-05-01', 10, 1);