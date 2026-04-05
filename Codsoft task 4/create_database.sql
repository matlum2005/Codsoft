-- Create Currency Converter Database
CREATE DATABASE IF NOT EXISTS currency_db 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

USE currency_db;

-- Conversion History Table
CREATE TABLE IF NOT EXISTS conversion_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    base_currency VARCHAR(3) NOT NULL,
    target_currency VARCHAR(3) NOT NULL,
    amount DECIMAL(15,2) NOT NULL,
    converted_amount DECIMAL(15,2) NOT NULL,
    rate DECIMAL(10,6) NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Index for faster queries
CREATE INDEX idx_conversion_history ON conversion_history(base_currency, target_currency, timestamp);

