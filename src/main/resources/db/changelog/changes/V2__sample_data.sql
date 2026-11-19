INSERT INTO roles (name) VALUES ('USER'), ('ADMIN');

-- Demo account: demo@carmarket.local / password
INSERT INTO users (email, password_hash, first_name, last_name, phone)
VALUES ('demo@carmarket.local', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
        'Demo', 'Driver', '+359 88 123 4567');

INSERT INTO brands (name) VALUES
    ('BMW'), ('Mercedes-Benz'), ('Volkswagen'), ('Audi'), ('Toyota'), ('Tesla');

INSERT INTO car_models (name, brand_id) VALUES
    ('3 Series', (SELECT id FROM brands WHERE name = 'BMW')),
    ('X5', (SELECT id FROM brands WHERE name = 'BMW')),
    ('C-Class', (SELECT id FROM brands WHERE name = 'Mercedes-Benz')),
    ('E-Class', (SELECT id FROM brands WHERE name = 'Mercedes-Benz')),
    ('Golf', (SELECT id FROM brands WHERE name = 'Volkswagen')),
    ('Tiguan', (SELECT id FROM brands WHERE name = 'Volkswagen')),
    ('A4', (SELECT id FROM brands WHERE name = 'Audi')),
    ('Q5', (SELECT id FROM brands WHERE name = 'Audi')),
    ('RAV4', (SELECT id FROM brands WHERE name = 'Toyota')),
    ('Model 3', (SELECT id FROM brands WHERE name = 'Tesla'));

INSERT INTO cars (title, brand_id, model_id, year, price, fuel_type, transmission, status) VALUES
    ('BMW 320d M Sport', (SELECT id FROM brands WHERE name = 'BMW'), (SELECT id FROM car_models WHERE name = '3 Series'), 2020, 41900.00, 'DIESEL', 'AUTOMATIC', 'ACTIVE'),
    ('BMW X5 xDrive30d', (SELECT id FROM brands WHERE name = 'BMW'), (SELECT id FROM car_models WHERE name = 'X5'), 2019, 66900.00, 'DIESEL', 'AUTOMATIC', 'ACTIVE'),
    ('Mercedes-Benz C 220d', (SELECT id FROM brands WHERE name = 'Mercedes-Benz'), (SELECT id FROM car_models WHERE name = 'C-Class'), 2021, 54900.00, 'DIESEL', 'AUTOMATIC', 'ACTIVE'),
    ('Mercedes-Benz E 300', (SELECT id FROM brands WHERE name = 'Mercedes-Benz'), (SELECT id FROM car_models WHERE name = 'E-Class'), 2018, 48900.00, 'PETROL', 'AUTOMATIC', 'ACTIVE'),
    ('Volkswagen Golf 7 GTI', (SELECT id FROM brands WHERE name = 'Volkswagen'), (SELECT id FROM car_models WHERE name = 'Golf'), 2018, 36500.00, 'PETROL', 'AUTOMATIC', 'ACTIVE'),
    ('Volkswagen Tiguan 2.0 TDI', (SELECT id FROM brands WHERE name = 'Volkswagen'), (SELECT id FROM car_models WHERE name = 'Tiguan'), 2020, 45900.00, 'DIESEL', 'AUTOMATIC', 'ACTIVE'),
    ('Audi A4 40 TDI', (SELECT id FROM brands WHERE name = 'Audi'), (SELECT id FROM car_models WHERE name = 'A4'), 2021, 52500.00, 'DIESEL', 'AUTOMATIC', 'ACTIVE'),
    ('Audi Q5 45 TFSI', (SELECT id FROM brands WHERE name = 'Audi'), (SELECT id FROM car_models WHERE name = 'Q5'), 2019, 57900.00, 'PETROL', 'AUTOMATIC', 'ACTIVE'),
    ('Toyota RAV4 Hybrid', (SELECT id FROM brands WHERE name = 'Toyota'), (SELECT id FROM car_models WHERE name = 'RAV4'), 2022, 61500.00, 'HYBRID', 'AUTOMATIC', 'ACTIVE'),
    ('Tesla Model 3 Long Range', (SELECT id FROM brands WHERE name = 'Tesla'), (SELECT id FROM car_models WHERE name = 'Model 3'), 2021, 59800.00, 'ELECTRIC', 'AUTOMATIC', 'ACTIVE');

INSERT INTO car_images (car_id, url, is_primary)
SELECT id, 'https://images.unsplash.com/photo-1555215695-3004980ad54e?auto=format&fit=crop&w=1200&q=80', TRUE
FROM cars WHERE title = 'BMW 320d M Sport';

INSERT INTO car_images (car_id, url, is_primary)
SELECT id, 'https://images.unsplash.com/photo-1492144534655-ae79c964c9d7?auto=format&fit=crop&w=1200&q=80', TRUE
FROM cars WHERE title = 'Mercedes-Benz C 220d';

INSERT INTO car_images (car_id, url, is_primary)
SELECT id, 'https://images.unsplash.com/photo-1503376780353-7e6692767b70?auto=format&fit=crop&w=1200&q=80', TRUE
FROM cars WHERE title = 'Audi A4 40 TDI';

INSERT INTO car_images (car_id, url, is_primary)
SELECT id, 'https://images.unsplash.com/photo-1560958089-b8a1929cea89?auto=format&fit=crop&w=1200&q=80', TRUE
FROM cars WHERE title = 'Tesla Model 3 Long Range';

INSERT INTO favorites (user_id, car_id)
SELECT (SELECT id FROM users WHERE email = 'demo@carmarket.local'), id
FROM cars WHERE title IN ('BMW 320d M Sport', 'Tesla Model 3 Long Range');
