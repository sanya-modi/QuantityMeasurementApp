-- Create users table
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    google_id VARCHAR(128) UNIQUE,
    email VARCHAR(320) UNIQUE NOT NULL,
    password VARCHAR(255),
    name VARCHAR(255),
    given_name VARCHAR(255),
    family_name VARCHAR(255),
    picture_url TEXT,
    locale VARCHAR(32),
    email_verified BOOLEAN DEFAULT FALSE,
    auth_provider VARCHAR(32) NOT NULL DEFAULT 'LOCAL',
    role VARCHAR(32) NOT NULL DEFAULT 'USER',
    enabled BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_login_at TIMESTAMP NULL
);

-- Create indexes for users
CREATE UNIQUE INDEX IF NOT EXISTS idx_google_id ON users(google_id);
CREATE UNIQUE INDEX IF NOT EXISTS idx_email ON users(email);

-- Create quantity_measurement_entity table
CREATE TABLE IF NOT EXISTS quantity_measurement_entity (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    this_value DOUBLE PRECISION NOT NULL,
    this_unit VARCHAR(50) NOT NULL,
    this_measurement_type VARCHAR(50) NOT NULL,
    that_value DOUBLE PRECISION,
    that_unit VARCHAR(50),
    that_measurement_type VARCHAR(50),
    operation VARCHAR(20) NOT NULL,
    result_value DOUBLE PRECISION,
    result_unit VARCHAR(50),
    result_measurement_type VARCHAR(50),
    result_string VARCHAR(255),
    is_error BOOLEAN DEFAULT FALSE,
    error_message VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes for quantity_measurement_entity
CREATE INDEX IF NOT EXISTS idx_operation ON quantity_measurement_entity(operation);
CREATE INDEX IF NOT EXISTS idx_measurement_type ON quantity_measurement_entity(this_measurement_type);
CREATE INDEX IF NOT EXISTS idx_is_error ON quantity_measurement_entity(is_error);
CREATE INDEX IF NOT EXISTS idx_user_id ON quantity_measurement_entity(user_id);

-- Insert sample data for users
INSERT INTO users (
    google_id, email, password, name, given_name, family_name,
    locale, email_verified, auth_provider, role, enabled
) VALUES
(
    'google_001',
    'testuser@example.com',
    '$2a$10$dXJ3SW6G7P50eS3Q7qsSXuRYFLAMh6uCHmqJgaI1.F1mF5U5mZM3K',
    'Test User',
    'Test',
    'User',
    'en_US',
    TRUE,
    'LOCAL',
    'USER',
    TRUE
),
(
    'google_002',
    'admin@example.com',
    '$2a$10$dXJ3SW6G7P50eS3Q7qsSXuRYFLAMh6uCHmqJgaI1.F1mF5U5mZM3K',
    'Admin User',
    'Admin',
    'User',
    'en_US',
    TRUE,
    'LOCAL',
    'ADMIN',
    TRUE
),
(
    'google_003',
    'johndoe@example.com',
    '$2a$10$dXJ3SW6G7P50eS3Q7qsSXuRYFLAMh6uCHmqJgaI1.F1mF5U5mZM3K',
    'John Doe',
    'John',
    'Doe',
    'en_US',
    TRUE,
    'GOOGLE',
    'USER',
    TRUE
)
ON CONFLICT DO NOTHING;

-- Insert sample data for measurements
INSERT INTO quantity_measurement_entity (
    user_id, this_value, this_unit, this_measurement_type,
    that_value, that_unit, that_measurement_type,
    operation, result_value, result_unit, result_measurement_type,
    result_string, is_error, error_message
) VALUES
(
    1, 100, 'm', 'LENGTH',
    1000, 'cm', 'LENGTH',
    'COMPARE', 1000, 'cm', 'LENGTH',
    '100 m = 10000 cm', FALSE, NULL
),
(
    1, 50, 'kg', 'WEIGHT',
    50000, 'g', 'WEIGHT',
    'COMPARE', 50000, 'g', 'WEIGHT',
    '50 kg = 50000 g', FALSE, NULL
),
(
    1, 1, 'L', 'VOLUME',
    1000, 'ml', 'VOLUME',
    'COMPARE', 1000, 'ml', 'VOLUME',
    '1 L = 1000 ml', FALSE, NULL
),
(
    2, 100, 'm', 'LENGTH',
    0, NULL, NULL,
    'ADD', NULL, NULL, NULL,
    'Cannot add single unit', FALSE, NULL
),
(
    3, 5, 'km', 'LENGTH',
    2000, 'm', 'LENGTH',
    'COMPARE', 5000, 'm', 'LENGTH',
    '5 km = 5000 m', FALSE, NULL
);

