
INSERT INTO address VALUES (1, "Hannover", "30457", "Ricklinger Stadtweg");
INSERT INTO customer VALUES (1, "Bank Bla Bli Blub", '2000-12-19', "yeet@yeet.com", "Student", "YA", "Yeet", "Forever Alone", "123456789", 1);
--Mann muss einen Postman Beispiel dazu hinzufügen
-- POST localhost:8080/customer/1/policy
-- {
--"startDate": "1990-01-04",
--  "endDate": "2030-12-31",
--  "coverage": 50000,
--  "objectOfInsurance": {
--    "name": "Tomato",
--    "race": "Bengal",
--    "color": "Braun",
--    "dateOfBirth": "2015-07-22",
--    "castrated": true,
--    "personality": "anhänglich",
--    "environment": "drinnen",
--    "weight": 4
--  }
--}


-- Customer 1
INSERT INTO address (id, city, postal_code, street) VALUES (10, 'New York', '10001', '123 Main St');
INSERT INTO customer (id, bank_details, date_of_birth, e_mail, employment_status, first_name, last_name, martial_status, phone_number, address_id) VALUES (10, '123456789', '1990-01-01', 'customer1@example.com', 'employed', 'John', 'Doe', 'single', '555-555-5555', 10);
INSERT INTO object_of_insurance (id, castrated, color, date_of_birth, environment, name, personality, race, weight) VALUES (10, 0, 'brown', '2018-01-01', 'outdoor', 'Rover', 'friendly', 'mixed breed', 50.0);
INSERT INTO policy (id, coverage, end_date, premium, start_date, customer_id, object_of_insurance_id) VALUES (10, 10000, '2024-01-01', 200.0, '2022-01-01', 10, 10);

-- Customer 2
INSERT INTO address (id, city, postal_code, street) VALUES (11, 'San Francisco', '94103', '456 Elm St');
INSERT INTO customer (id, bank_details, date_of_birth, e_mail, employment_status, first_name, last_name, martial_status, phone_number, address_id) VALUES (11, '987654321', '1985-02-15', 'customer2@example.com', 'unemployed', 'Jane', 'Smith', 'married', '555-555-5556', 11);
INSERT INTO object_of_insurance (id, castrated, color, date_of_birth, environment, name, personality, race, weight) VALUES (11, 1, 'black', '2019-03-15', 'indoor', 'Fluffy', 'shy', 'domestic short hair', 10.0);
INSERT INTO policy (id, coverage, end_date, premium, start_date, customer_id, object_of_insurance_id) VALUES (11, 5000, '2023-02-15', 150.0, '2021-02-15', 11, 11);

-- Customer 3
INSERT INTO address (id, city, postal_code, street) VALUES (12, 'Chicago', '60601', '789 Oak St');
INSERT INTO customer (id, bank_details, date_of_birth, e_mail, employment_status, first_name, last_name, martial_status, phone_number, address_id) VALUES (12, '111222333', '1978-05-20', 'customer3@example.com', 'self-employed', 'Bob', 'Johnson', 'divorced', '555-555-5557', 12);
INSERT INTO object_of_insurance (id, castrated, color, date_of_birth, environment, name, personality, race, weight) VALUES (12, 0, 'white', '2020-06-20', 'outdoor', 'Max', 'energetic', 'golden retriever', 70.0);
INSERT INTO policy (id, coverage, end_date, premium, start_date, customer_id, object_of_insurance_id) VALUES (12, 20000, '2025-05-20', 300.0, '2023-05-20', 12, 12);

-- Customer 4
INSERT INTO address (id, city, postal_code, street) VALUES (13, 'Los Angeles', '90001', '789 Main St');
INSERT INTO customer (id, bank_details, date_of_birth, e_mail, employment_status, first_name, last_name, martial_status, phone_number, address_id) VALUES (13, '444555666', '1995-08-31', 'customer4@example.com', 'employed', 'Alice', 'Lee', 'single', '555-555-5558', 13);
INSERT INTO object_of_insurance (id, castrated, color, date_of_birth, environment, name, personality, race, weight) VALUES (13, 1, 'brown', '2021-01-01', 'indoor', 'Mittens', 'playful', 'siamese', 5.0);
INSERT INTO policy (id, coverage, end_date, premium, start_date, customer_id, object_of_insurance_id) VALUES (13, 5000, '2024-08-31', 100.0, '2022-08-31', 13, 13);

-- Customer 5
INSERT INTO address (id, city, postal_code, street) VALUES (14, 'Miami', '33101', '456 Park Ave');
INSERT INTO customer (id, bank_details, date_of_birth, e_mail, employment_status, first_name, last_name, martial_status, phone_number, address_id) VALUES (14, '777888999', '1980-12-25', 'customer5@example.com', 'self-employed', 'David', 'Nguyen', 'married', '555-555-5559', 14);
INSERT INTO object_of_insurance (id, castrated, color, date_of_birth, environment, name, personality, race, weight) VALUES (14, 0, 'gray', '2017-04-15', 'outdoor', 'Buddy', 'loyal', 'labrador retriever', 80.0);
INSERT INTO policy (id, coverage, end_date, premium, start_date, customer_id, object_of_insurance_id) VALUES (14, 15000, '2023-12-25', 250.0, '2021-12-25', 14, 14);

-- Customer 6
INSERT INTO address (id, city, postal_code, street) VALUES (15, 'Houston', '77001', '123 Elm St');
INSERT INTO customer (id, bank_details, date_of_birth, e_mail, employment_status, first_name, last_name, martial_status, phone_number, address_id) VALUES (15, '000111222', '1975-06-10', 'customer6@example.com', 'unemployed', 'Lisa', 'Garcia', 'divorced', '555-555-5560', 15);
INSERT INTO object_of_insurance (id, castrated, color, date_of_birth, environment, name, personality, race, weight) VALUES (15, 1, 'white', '2018-11-20', 'indoor', 'Whiskers', 'affectionate', 'persian', 8.0);
INSERT INTO policy (id, coverage, end_date, premium, start_date, customer_id, object_of_insurance_id) VALUES (15, 1000, '2022-06-10', 50.0, '2020-06-10', 15, 15);