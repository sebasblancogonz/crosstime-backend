-- Create schedules table
CREATE TABLE IF NOT EXISTS schedules (
    id varbinary(36) NOT NULL,
    day_of_week ENUM('MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY') NOT NULL,
    time_of_day TIME NOT NULL,
    PRIMARY KEY (id)
);

-- Create slots table
CREATE TABLE IF NOT EXISTS slots (
    id varbinary(36) NOT NULL,
    capacity INT NOT NULL,
    date_time DATETIME NOT NULL,
    duration INT NOT NULL,
    training_type ENUM('FUNCTIONAL', 'WOD', 'WEIGHTLIFTING', 'GYMNASTICS', 'CROSSFIT', 'PILATES', 'YOGA', 'SPINNING', 'BOXING') NOT NULL,
    instructor_id varbinary(36),
    schedule_id varbinary(36),
    PRIMARY KEY (id),
    CONSTRAINT fk_instructor
        FOREIGN KEY (instructor_id) REFERENCES users(id) ON DELETE SET NULL
);

-- Create table reservations
CREATE TABLE IF NOT EXISTS reservations (
    id varbinary(36) NOT NULL,
    reservation_date DATE NOT NULL,
    user_id varbinary(36),
    slot_id varbinary(36),
    PRIMARY KEY (id),
    CONSTRAINT fk_user
        FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_slot
        FOREIGN KEY (slot_id) REFERENCES slots(id) ON DELETE CASCADE
);

-- Create exceptions table
CREATE TABLE IF NOT EXISTS schedule_exceptions (
    id varbinary(36) NOT NULL,
    specific_date DATE NOT NULL,
    slot_id varbinary(36),
    exception_type ENUM('CANCEL', 'ADDITIONAL') NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_slot_exception
        FOREIGN KEY (slot_id) REFERENCES slots(id) ON DELETE CASCADE
);

CREATE INDEX idx_specific_date_slot ON schedule_exceptions(specific_date, slot_id);

-- Alter users table to add userType column
ALTER TABLE users
ADD COLUMN user_type ENUM('COACH', 'ATHLETE') NOT NULL DEFAULT 'ATHLETE';