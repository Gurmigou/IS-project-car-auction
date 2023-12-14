INSERT INTO car_make (name) VALUES ('Audi');
INSERT INTO car_make (name) VALUES ('BMW');
INSERT INTO car_make (name) VALUES ('Volkswagen');
INSERT INTO car_make (name) VALUES ('Mercedes-Benz');
INSERT INTO car_make (name) VALUES ('Toyota');
INSERT INTO car_make (name) VALUES ('Honda');
INSERT INTO car_make (name) VALUES ('Ford');
INSERT INTO car_make (name) VALUES ('Chevrolet');
INSERT INTO car_make (name) VALUES ('Nissan');
INSERT INTO car_make (name) VALUES ('Hyundai');
INSERT INTO car_make (name) VALUES ('Kia');
INSERT INTO car_make (name) VALUES ('Jeep');
INSERT INTO car_make (name) VALUES ('Subaru');
INSERT INTO car_make (name) VALUES ('Mazda');
INSERT INTO car_make (name) VALUES ('Tesla');
INSERT INTO car_make (name) VALUES ('Lexus');
INSERT INTO car_make (name) VALUES ('Volvo');
INSERT INTO car_make (name) VALUES ('Porsche');
INSERT INTO car_make (name) VALUES ('Infinite');


-- Assuming Audi has ID 1, BMW has ID 2, and Volkswagen has ID 3
-- Insert models for Audi
INSERT INTO car_model (name, car_make_id) VALUES ('A4', 1);
INSERT INTO car_model (name, car_make_id) VALUES ('A6', 1);
INSERT INTO car_model (name, car_make_id) VALUES ('Q5', 1);

-- Insert models for BMW
INSERT INTO car_model (name, car_make_id) VALUES ('3 Series', 2);
INSERT INTO car_model (name, car_make_id) VALUES ('5 Series', 2);
INSERT INTO car_model (name, car_make_id) VALUES ('X3', 2);

-- Insert models for Volkswagen
INSERT INTO car_model (name, car_make_id) VALUES ('Golf', 3);
INSERT INTO car_model (name, car_make_id) VALUES ('Passat', 3);
INSERT INTO car_model (name, car_make_id) VALUES ('Tiguan', 3);

-- Insert models for Mercedes-Benz (ID 4)
INSERT INTO car_model (name, car_make_id) VALUES ('C-Class', 4);
INSERT INTO car_model (name, car_make_id) VALUES ('E-Class', 4);
INSERT INTO car_model (name, car_make_id) VALUES ('GLC', 4);

-- Insert models for Toyota (ID 5)
INSERT INTO car_model (name, car_make_id) VALUES ('Corolla', 5);
INSERT INTO car_model (name, car_make_id) VALUES ('Camry', 5);
INSERT INTO car_model (name, car_make_id) VALUES ('RAV4', 5);

-- Insert models for Honda (ID 6)
INSERT INTO car_model (name, car_make_id) VALUES ('Civic', 6);
INSERT INTO car_model (name, car_make_id) VALUES ('Accord', 6);
INSERT INTO car_model (name, car_make_id) VALUES ('CR-V', 6);

-- Insert models for Ford (ID 7)
INSERT INTO car_model (name, car_make_id) VALUES ('Fiesta', 7);
INSERT INTO car_model (name, car_make_id) VALUES ('Focus', 7);
INSERT INTO car_model (name, car_make_id) VALUES ('Mustang', 7);

-- Insert models for Chevrolet (ID 8)
INSERT INTO car_model (name, car_make_id) VALUES ('Cruze', 8);
INSERT INTO car_model (name, car_make_id) VALUES ('Malibu', 8);
INSERT INTO car_model (name, car_make_id) VALUES ('Silverado', 8);

-- Insert models for Nissan (ID 9)
INSERT INTO car_model (name, car_make_id) VALUES ('Sentra', 9);
INSERT INTO car_model (name, car_make_id) VALUES ('Altima', 9);
INSERT INTO car_model (name, car_make_id) VALUES ('Rogue', 9);

-- Insert models for Hyundai (ID 10)
INSERT INTO car_model (name, car_make_id) VALUES ('Elantra', 10);
INSERT INTO car_model (name, car_make_id) VALUES ('Sonata', 10);
INSERT INTO car_model (name, car_make_id) VALUES ('Tucson', 10);

-- Insert models for Kia (ID 11)
INSERT INTO car_model (name, car_make_id) VALUES ('Forte', 11);
INSERT INTO car_model (name, car_make_id) VALUES ('Optima', 11);
INSERT INTO car_model (name, car_make_id) VALUES ('Sorento', 11);

-- Insert models for Jeep (ID 12)
INSERT INTO car_model (name, car_make_id) VALUES ('Wrangler', 12);
INSERT INTO car_model (name, car_make_id) VALUES ('Cherokee', 12);
INSERT INTO car_model (name, car_make_id) VALUES ('Grand Cherokee', 12);

-- Insert models for Subaru (ID 13)
INSERT INTO car_model (name, car_make_id) VALUES ('Impreza', 13);
INSERT INTO car_model (name, car_make_id) VALUES ('Outback', 13);
INSERT INTO car_model (name, car_make_id) VALUES ('Forester', 13);

-- Insert models for Mazda (ID 14)
INSERT INTO car_model (name, car_make_id) VALUES ('Mazda3', 14);
INSERT INTO car_model (name, car_make_id) VALUES ('Mazda6', 14);
INSERT INTO car_model (name, car_make_id) VALUES ('CX-5', 14);

-- Insert models for Tesla (ID 15)
INSERT INTO car_model (name, car_make_id) VALUES ('Model S', 15);
INSERT INTO car_model (name, car_make_id) VALUES ('Model 3', 15);
INSERT INTO car_model (name, car_make_id) VALUES ('Model X', 15);

-- Insert models for Lexus (ID 16)
INSERT INTO car_model (name, car_make_id) VALUES ('IS', 16);
INSERT INTO car_model (name, car_make_id) VALUES ('ES', 16);
INSERT INTO car_model (name, car_make_id) VALUES ('RX', 16);

-- Insert models for Volvo (ID 17)
INSERT INTO car_model (name, car_make_id) VALUES ('S60', 17);
INSERT INTO car_model (name, car_make_id) VALUES ('XC60', 17);
INSERT INTO car_model (name, car_make_id) VALUES ('XC90', 17);

-- Insert models for Porsche (ID 18)
INSERT INTO car_model (name, car_make_id) VALUES ('911', 18);
INSERT INTO car_model (name, car_make_id) VALUES ('Cayenne', 18);
INSERT INTO car_model (name, car_make_id) VALUES ('Panamera', 18);
INSERT INTO car_model (name, car_make_id) VALUES ('Taycan', 18);

-- Insert models for Infinite (ID 19)
INSERT INTO car_model (name, car_make_id) VALUES ('Q50', 19);
INSERT INTO car_model (name, car_make_id) VALUES ('Q60', 19);
INSERT INTO car_model (name, car_make_id) VALUES ('QX70', 19);


INSERT INTO user (email, first_name, last_name, password) VALUES ('some@gmail.com', 'John', 'Doe', '123456');
INSERT INTO insurance_company (name, owner_id) VALUES ('Insurance Company 1', 1);