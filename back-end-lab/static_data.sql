INSERT INTO car_make (name) VALUES ('Audi');
INSERT INTO car_make (name) VALUES ('BMW');
INSERT INTO car_make (name) VALUES ('Volkswagen');

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
