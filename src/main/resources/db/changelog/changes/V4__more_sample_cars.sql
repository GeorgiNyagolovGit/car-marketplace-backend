INSERT INTO brands (name) VALUES
    ('Ford'), ('Honda'), ('Hyundai'), ('Kia'), ('Mazda'), ('Nissan'), ('Peugeot'), ('Renault'), ('Skoda'), ('Volvo')
ON CONFLICT (name) DO NOTHING;

INSERT INTO car_models (name, brand_id)
SELECT model_name, b.id
FROM (VALUES
    ('Ford', 'Mustang'),
    ('Honda', 'Civic'),
    ('Hyundai', 'Tucson'),
    ('Kia', 'Sportage'),
    ('Mazda', 'CX-5'),
    ('Nissan', 'Qashqai'),
    ('Peugeot', '3008'),
    ('Renault', 'Clio'),
    ('Skoda', 'Octavia'),
    ('Volvo', 'XC60')
) AS data(brand_name, model_name)
JOIN brands b ON b.name = data.brand_name
WHERE NOT EXISTS (
    SELECT 1 FROM car_models cm
    WHERE cm.name = data.model_name AND cm.brand_id = b.id
);

INSERT INTO cars (title, brand_id, model_id, year, price, fuel_type, transmission, status)
SELECT data.title, b.id, cm.id, data.car_year, data.price, data.fuel_type, data.transmission, 'ACTIVE'
FROM (VALUES
    ('Ford Mustang GT', 'Ford', 'Mustang', 2020, 72900.00, 'PETROL', 'AUTOMATIC'),
    ('Honda Civic 1.5 VTEC', 'Honda', 'Civic', 2021, 38900.00, 'PETROL', 'MANUAL'),
    ('Hyundai Tucson Hybrid', 'Hyundai', 'Tucson', 2022, 55900.00, 'HYBRID', 'AUTOMATIC'),
    ('Kia Sportage 1.6 T-GDI', 'Kia', 'Sportage', 2023, 57900.00, 'PETROL', 'AUTOMATIC'),
    ('Mazda CX-5 Skyactiv-G', 'Mazda', 'CX-5', 2021, 46900.00, 'PETROL', 'AUTOMATIC'),
    ('Nissan Qashqai e-POWER', 'Nissan', 'Qashqai', 2023, 62900.00, 'HYBRID', 'AUTOMATIC'),
    ('Peugeot 3008 BlueHDi', 'Peugeot', '3008', 2020, 39900.00, 'DIESEL', 'AUTOMATIC'),
    ('Renault Clio TCe', 'Renault', 'Clio', 2022, 28900.00, 'PETROL', 'MANUAL'),
    ('Skoda Octavia 2.0 TDI', 'Skoda', 'Octavia', 2021, 42900.00, 'DIESEL', 'AUTOMATIC'),
    ('Volvo XC60 Recharge', 'Volvo', 'XC60', 2022, 84900.00, 'HYBRID', 'AUTOMATIC')
) AS data(title, brand_name, model_name, car_year, price, fuel_type, transmission)
JOIN brands b ON b.name = data.brand_name
JOIN car_models cm ON cm.name = data.model_name AND cm.brand_id = b.id
WHERE NOT EXISTS (SELECT 1 FROM cars c WHERE c.title = data.title);
