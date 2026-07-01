-- Create Database
CREATE DATABASE IF NOT EXISTS barber_db;
USE barber_db;

-- Users Table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    userName VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    gender VARCHAR(50) DEFAULT 'Not Selected',
    dob VARCHAR(50) DEFAULT 'Not Selected',
    image LONGTEXT,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Barbers Table
CREATE TABLE IF NOT EXISTS barbers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    image LONGTEXT NOT NULL,
    about TEXT NOT NULL,
    available BOOLEAN DEFAULT TRUE,
    slotsBooked JSON DEFAULT '{}',
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_email (email),
    INDEX idx_available (available)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Appointments Table
CREATE TABLE IF NOT EXISTS appointments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    userId BIGINT NOT NULL,
    barberId BIGINT NOT NULL,
    slotDate VARCHAR(100) NOT NULL,
    slotTime VARCHAR(50) NOT NULL,
    userData JSON NOT NULL,
    barberData JSON NOT NULL,
    cancelled BOOLEAN DEFAULT FALSE,
    isCompleted BOOLEAN DEFAULT FALSE,
    message TEXT DEFAULT '',
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (userId) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (barberId) REFERENCES barbers(id) ON DELETE CASCADE,
    INDEX idx_userId (userId),
    INDEX idx_barberId (barberId),
    INDEX idx_slotDate (slotDate),
    INDEX idx_cancelled (cancelled)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Admin Users Table
CREATE TABLE IF NOT EXISTS admins (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insert default admin (password: admin@123)
INSERT IGNORE INTO admins (email, password) VALUES ('admin@barbershop.com', '$2a$10$slYQmyNdGzin7olVN3p5Be7DlH.PKZbv5H8KnzzVgXXbVxzy2QDBG');

COMMIT;