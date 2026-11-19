-- Complete demo listings used by the customer-facing frontend.
-- Every insert is guarded so the seed remains safe when a developer has
-- already created one of the listings manually.

INSERT INTO brands (name) VALUES ('Porsche')
ON CONFLICT (name) DO NOTHING;

INSERT INTO car_models (name, brand_id)
SELECT data.model_name, brand.id
FROM (VALUES
    ('BMW', '3 Series'),
    ('Mercedes-Benz', 'C-Class'),
    ('Audi', 'Q5'),
    ('Volkswagen', 'Golf'),
    ('Volvo', 'XC60'),
    ('Porsche', '911'),
    ('Tesla', 'Model 3'),
    ('Ford', 'Mustang')
) AS data(brand_name, model_name)
JOIN brands brand ON brand.name = data.brand_name
WHERE NOT EXISTS (
    SELECT 1
    FROM car_models model
    WHERE model.brand_id = brand.id
      AND model.name = data.model_name
);

INSERT INTO cars (title, brand_id, model_id, year, price, fuel_type, transmission, status)
SELECT data.title,
       brand.id,
       model.id,
       data.car_year,
       data.price,
       data.fuel_type,
       data.transmission,
       'ACTIVE'
FROM (VALUES
    ('BMW 330d xDrive M Sport', 'BMW', '3 Series', 2021, 43750.00, 'DIESEL', 'AUTOMATIC'),
    ('Mercedes-Benz C 220 d AMG', 'Mercedes-Benz', 'C-Class', 2022, 51200.00, 'DIESEL', 'AUTOMATIC'),
    ('Audi Q5 40 TDI quattro', 'Audi', 'Q5', 2021, 58400.00, 'DIESEL', 'AUTOMATIC'),
    ('Volkswagen Golf 1.5 eTSI Style', 'Volkswagen', 'Golf', 2022, 32900.00, 'HYBRID', 'AUTOMATIC'),
    ('Volvo XC60 B5 AWD Inscription', 'Volvo', 'XC60', 2022, 67900.00, 'HYBRID', 'AUTOMATIC'),
    ('Porsche 911 Carrera S', 'Porsche', '911', 2020, 184900.00, 'PETROL', 'AUTOMATIC'),
    ('Tesla Model 3 Long Range AWD', 'Tesla', 'Model 3', 2022, 45900.00, 'ELECTRIC', 'AUTOMATIC'),
    ('Ford Mustang GT 5.0 V8', 'Ford', 'Mustang', 2020, 73900.00, 'PETROL', 'AUTOMATIC')
) AS data(title, brand_name, model_name, car_year, price, fuel_type, transmission)
JOIN brands brand ON brand.name = data.brand_name
JOIN car_models model
  ON model.brand_id = brand.id
 AND model.name = data.model_name
WHERE NOT EXISTS (
    SELECT 1
    FROM cars car
    WHERE car.title = data.title
);

INSERT INTO car_images (car_id, url, is_primary)
SELECT car.id, data.url, TRUE
FROM (VALUES
    ('BMW 330d xDrive M Sport', 'https://images.unsplash.com/photo-1555215695-3004980ad54e?auto=format&fit=crop&w=1400&q=85'),
    ('Mercedes-Benz C 220 d AMG', 'https://images.unsplash.com/photo-1618843479313-40f8afb4b4d8?auto=format&fit=crop&w=1400&q=85'),
    ('Audi Q5 40 TDI quattro', 'https://images.unsplash.com/photo-1606664515524-ed2f786a0bd6?auto=format&fit=crop&w=1400&q=85'),
    ('Volkswagen Golf 1.5 eTSI Style', 'https://images.unsplash.com/photo-1541899481282-d53bffe3c35d?auto=format&fit=crop&w=1400&q=85'),
    ('Volvo XC60 B5 AWD Inscription', 'https://images.unsplash.com/photo-1619767886558-efdc259cde1a?auto=format&fit=crop&w=1400&q=85'),
    ('Porsche 911 Carrera S', 'https://images.unsplash.com/photo-1503376780353-7e6692767b70?auto=format&fit=crop&w=1400&q=85'),
    ('Tesla Model 3 Long Range AWD', 'https://images.unsplash.com/photo-1560958089-b8a1929cea89?auto=format&fit=crop&w=1400&q=85'),
    ('Ford Mustang GT 5.0 V8', 'https://images.unsplash.com/photo-1494976388531-d1058494cdd8?auto=format&fit=crop&w=1400&q=85')
) AS data(title, url)
JOIN cars car ON car.title = data.title
WHERE NOT EXISTS (
    SELECT 1
    FROM car_images image
    WHERE image.car_id = car.id
      AND image.is_primary = TRUE
);
