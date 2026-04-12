-- ================================================================
-- Complete Database Setup for Quantity Measurement Application
-- This script sets up both measurement-service and user-service databases
-- ================================================================

-- ================================================================
-- MEASUREMENT SERVICE - measurementdb
-- ================================================================

-- Create the database
CREATE DATABASE IF NOT EXISTS measurementdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE measurementdb;

-- Create the quantity_measurement_entity table
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

    KEY idx_operation (operation),
    KEY idx_measurement_type (this_measurement_type),
    KEY idx_is_error (is_error),
    KEY idx_user_id (user_id),
    KEY idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insert sample data for measurement-service
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
-- USER SERVICE - user_db
-- ================================================================

-- Create the database
CREATE DATABASE IF NOT EXISTS user_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE user_db;

-- Create the users table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    google_id VARCHAR(128) UNIQUE,
    email VARCHAR(320) NOT NULL UNIQUE,
    password VARCHAR(255),
    name VARCHAR(255),
    given_name VARCHAR(255),
    family_name VARCHAR(255),
    picture_url LONGTEXT,
    locale VARCHAR(32),
    email_verified BOOLEAN DEFAULT FALSE,
    auth_provider VARCHAR(32) NOT NULL DEFAULT 'LOCAL' COMMENT 'LOCAL, GOOGLE, HYBRID',
    role VARCHAR(32) NOT NULL DEFAULT 'USER' COMMENT 'USER, ADMIN',
    enabled BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    last_login_at TIMESTAMP NULL,

    UNIQUE KEY idx_google_id (google_id),
    UNIQUE KEY idx_email (email),
    KEY idx_auth_provider (auth_provider),
    KEY idx_role (role),
    KEY idx_enabled (enabled),
    KEY idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insert sample data for user-service

-- LOCAL authentication users
INSERT INTO users
(email, password, name, given_name, family_name, auth_provider, role, enabled, email_verified, created_at, updated_at)
VALUES
('user1@example.com', '$2a$10$slYQmyNdGzin7olVN3p5be.MaZt9FvexcqmDvW4uPLqGMKKH7Xua6', 'John Doe', 'John', 'Doe', 'LOCAL', 'USER', TRUE, TRUE, NOW(), NOW()),
('user2@example.com', '$2a$10$slYQmyNdGzin7olVN3p5be.MaZt9FvexcqmDvW4uPLqGMKKH7Xua6', 'Jane Smith', 'Jane', 'Smith', 'LOCAL', 'USER', TRUE, TRUE, NOW(), NOW()),
('admin@example.com', '$2a$10$slYQmyNdGzin7olVN3p5be.MaZt9FvexcqmDvW4uPLqGMKKH7Xua6', 'Admin User', 'Admin', 'User', 'LOCAL', 'ADMIN', TRUE, TRUE, NOW(), NOW());

-- GOOGLE OAuth2 users
INSERT INTO users
(google_id, email, name, given_name, family_name, picture_url, locale, auth_provider, role, enabled, email_verified, created_at, updated_at, last_login_at)
VALUES
('google_123456789', 'alice.google@gmail.com', 'Alice Johnson', 'Alice', 'Johnson', 'https://example.com/alice.jpg', 'en', 'GOOGLE', 'USER', TRUE, TRUE, NOW(), NOW(), NOW()),
('google_987654321', 'bob.google@gmail.com', 'Bob Wilson', 'Bob', 'Wilson', 'https://example.com/bob.jpg', 'en', 'GOOGLE', 'USER', TRUE, TRUE, NOW(), NOW(), NOW());

-- HYBRID authentication user
INSERT INTO users
(google_id, email, password, name, given_name, family_name, picture_url, locale, auth_provider, role, enabled, email_verified, created_at, updated_at, last_login_at)
VALUES
('google_555555555', 'charlie@example.com', '$2a$10$slYQmyNdGzin7olVN3p5be.MaZt9FvexcqmDvW4uPLqGMKKH7Xua6', 'Charlie Brown', 'Charlie', 'Brown', 'https://example.com/charlie.jpg', 'en', 'HYBRID', 'USER', TRUE, TRUE, NOW(), NOW(), NOW());

-- Disabled user
INSERT INTO users
(email, password, name, given_name, family_name, auth_provider, role, enabled, email_verified, created_at, updated_at)
VALUES
('disabled@example.com', '$2a$10$slYQmyNdGzin7olVN3p5be.MaZt9FvexcqmDvW4uPLqGMKKH7Xua6', 'Disabled User', 'Disabled', 'User', 'LOCAL', 'USER', FALSE, FALSE, NOW(), NOW());

-- ================================================================
-- Verification Queries (commented out for batch execution)
-- ================================================================
-- USE measurementdb;
-- SELECT 'Measurement Service Data:' as 'Status';
-- SELECT COUNT(*) as total_records FROM quantity_measurement_entity;
-- SELECT * FROM quantity_measurement_entity LIMIT 5;

-- USE user_db;
-- SELECT 'User Service Data:' as 'Status';
-- SELECT COUNT(*) as total_users FROM users;
-- SELECT id, email, auth_provider, role, enabled FROM users;

