-- ================================================================
-- Measurement Service Database Setup
-- Database: measurementdb
-- ================================================================

-- Create the database if it doesn't exist
-- CREATE DATABASE IF NOT EXISTS measurementdb;
-- USE measurementdb;

-- ================================================================
-- Table: quantity_measurement_entity
-- Description: Stores measurement records for quantity conversions
-- ================================================================
CREATE TABLE IF NOT EXISTS quantity_measurement_entity (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    this_value DOUBLE NOT NULL,
    this_unit VARCHAR(50) NOT NULL,
    this_measurement_type VARCHAR(50) NOT NULL,
    that_value DOUBLE,
    that_unit VARCHAR(50),
    that_measurement_type VARCHAR(50),
    operation VARCHAR(50) NOT NULL COMMENT 'ADD, SUBTRACT, MULTIPLY, DIVIDE, CONVERT',
    result_value DOUBLE,
    result_unit VARCHAR(50),
    result_measurement_type VARCHAR(50),
    result_string TEXT,
    is_error BOOLEAN NOT NULL DEFAULT FALSE,
    error_message TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    -- Indexes for performance
    KEY idx_operation (operation),
    KEY idx_measurement_type (this_measurement_type),
    KEY idx_is_error (is_error),
    KEY idx_user_id (user_id),
    KEY idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ================================================================
-- Sample Data for Testing
-- ================================================================

-- Insert sample measurement records
INSERT INTO quantity_measurement_entity
(user_id, this_value, this_unit, this_measurement_type, operation, result_value, result_unit, result_measurement_type, result_string, is_error, created_at, updated_at)
VALUES
(1, 1.0, 'METER', 'LENGTH', 'CONVERT', 3.28084, 'FEET', 'LENGTH', '1.0 METER = 3.28084 FEET', FALSE, NOW(), NOW()),
(1, 5.0, 'KILOMETER', 'LENGTH', 'CONVERT', 5000.0, 'METER', 'LENGTH', '5.0 KILOMETER = 5000.0 METER', FALSE, NOW(), NOW()),
(2, 1000.0, 'GRAM', 'WEIGHT', 'CONVERT', 1.0, 'KILOGRAM', 'WEIGHT', '1000.0 GRAM = 1.0 KILOGRAM', FALSE, NOW(), NOW()),
(2, 1.0, 'LITER', 'VOLUME', 'CONVERT', 1000.0, 'MILLILITER', 'VOLUME', '1.0 LITER = 1000.0 MILLILITER', FALSE, NOW(), NOW()),
(3, 100.0, 'METER', 'LENGTH', 'ADD', 150.0, 'METER', 'LENGTH', '100.0 METER + 50.0 METER = 150.0 METER', FALSE, NOW(), NOW()),
(3, 50.0, 'KILOGRAM', 'WEIGHT', 'SUBTRACT', 30.0, 'KILOGRAM', 'WEIGHT', '50.0 KILOGRAM - 20.0 KILOGRAM = 30.0 KILOGRAM', FALSE, NOW(), NOW()),
(1, 10.0, 'METER', 'LENGTH', 'MULTIPLY', 100.0, 'SQUARE_METER', 'AREA', '10.0 METER * 10.0 METER = 100.0 SQUARE_METER', FALSE, NOW(), NOW()),
(1, 100.0, 'METER', 'LENGTH', 'DIVIDE', 10.0, 'METER', 'LENGTH', '100.0 METER / 10.0 = 10.0 METER', FALSE, NOW(), NOW());

-- ================================================================
-- Verify the table creation and data insertion
-- ================================================================
-- SELECT * FROM quantity_measurement_entity;
-- SELECT COUNT(*) as total_records FROM quantity_measurement_entity;

