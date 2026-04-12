-- ================================================================
-- User Service Database Setup
-- Database: user_db
-- ================================================================

-- Create the database if it doesn't exist
-- CREATE DATABASE IF NOT EXISTS user_db;
-- USE user_db;

-- ================================================================
-- Table: users
-- Description: Stores user account information with authentication provider support
-- ================================================================
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

    -- Indexes for performance and uniqueness
    UNIQUE KEY idx_google_id (google_id),
    UNIQUE KEY idx_email (email),
    KEY idx_auth_provider (auth_provider),
    KEY idx_role (role),
    KEY idx_enabled (enabled),
    KEY idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ================================================================
-- Sample Data for Testing
-- ================================================================

-- Insert sample users - LOCAL authentication
INSERT INTO users
(email, password, name, given_name, family_name, auth_provider, role, enabled, email_verified, created_at, updated_at)
VALUES
('user1@example.com', '$2a$10$slYQmyNdGzin7olVN3p5be.MaZt9FvexcqmDvW4uPLqGMKKH7Xua6', 'John Doe', 'John', 'Doe', 'LOCAL', 'USER', TRUE, TRUE, NOW(), NOW()),
('user2@example.com', '$2a$10$slYQmyNdGzin7olVN3p5be.MaZt9FvexcqmDvW4uPLqGMKKH7Xua6', 'Jane Smith', 'Jane', 'Smith', 'LOCAL', 'USER', TRUE, TRUE, NOW(), NOW()),
('admin@example.com', '$2a$10$slYQmyNdGzin7olVN3p5be.MaZt9FvexcqmDvW4uPLqGMKKH7Xua6', 'Admin User', 'Admin', 'User', 'LOCAL', 'ADMIN', TRUE, TRUE, NOW(), NOW());

-- Insert sample users - GOOGLE authentication
INSERT INTO users
(google_id, email, name, given_name, family_name, picture_url, locale, auth_provider, role, enabled, email_verified, created_at, updated_at, last_login_at)
VALUES
('google_123456789', 'alice.google@gmail.com', 'Alice Johnson', 'Alice', 'Johnson', 'https://example.com/alice.jpg', 'en', 'GOOGLE', 'USER', TRUE, TRUE, NOW(), NOW(), NOW()),
('google_987654321', 'bob.google@gmail.com', 'Bob Wilson', 'Bob', 'Wilson', 'https://example.com/bob.jpg', 'en', 'GOOGLE', 'USER', TRUE, TRUE, NOW(), NOW(), NOW());

-- Insert sample user - HYBRID authentication (has both local and Google)
INSERT INTO users
(google_id, email, password, name, given_name, family_name, picture_url, locale, auth_provider, role, enabled, email_verified, created_at, updated_at, last_login_at)
VALUES
('google_555555555', 'charlie@example.com', '$2a$10$slYQmyNdGzin7olVN3p5be.MaZt9FvexcqmDvW4uPLqGMKKH7Xua6', 'Charlie Brown', 'Charlie', 'Brown', 'https://example.com/charlie.jpg', 'en', 'HYBRID', 'USER', TRUE, TRUE, NOW(), NOW(), NOW());

-- Insert a disabled user for testing
INSERT INTO users
(email, password, name, given_name, family_name, auth_provider, role, enabled, email_verified, created_at, updated_at)
VALUES
('disabled@example.com', '$2a$10$slYQmyNdGzin7olVN3p5be.MaZt9FvexcqmDvW4uPLqGMKKH7Xua6', 'Disabled User', 'Disabled', 'User', 'LOCAL', 'USER', FALSE, FALSE, NOW(), NOW());

-- ================================================================
-- Verify the table creation and data insertion
-- ================================================================
-- SELECT * FROM users;
-- SELECT COUNT(*) as total_users FROM users;
-- SELECT email, role, auth_provider, enabled FROM users;

