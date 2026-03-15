CREATE TABLE IF NOT EXISTS quantity_measurement (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    operation VARCHAR(100) NOT NULL,
    input_data TEXT NOT NULL,
    result_data TEXT,
    error_flag BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_quantity_measurement_operation ON quantity_measurement(operation);

CREATE INDEX idx_quantity_measurement_created_at ON quantity_measurement(created_at);

CREATE TABLE IF NOT EXISTS quantity_measurement_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    measurement_id BIGINT,
    operation VARCHAR(100) NOT NULL,
    input_data TEXT,
    result_data TEXT,
    error_flag BOOLEAN,
    changed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);