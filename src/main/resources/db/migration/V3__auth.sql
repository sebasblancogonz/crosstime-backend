ALTER TABLE users ADD role ENUM('admin', 'user') NOT NULL DEFAULT 'user';

-- TOKENS TABLE
CREATE TABLE tokens (
    id INT AUTO_INCREMENT PRIMARY KEY,
    token VARCHAR(255) NOT NULL,
    token_type ENUM('BEARER') NOT NULL,
    expired BOOLEAN NOT NULL DEFAULT FALSE,
    revoked BOOLEAN NOT NULL DEFAULT FALSE,
    user_id varbinary(36) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

