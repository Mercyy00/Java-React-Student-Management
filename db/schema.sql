-- Run this ONCE in MySQL before starting the app.
-- Command line:  mysql -u root -p < db/schema.sql
-- Or paste it into MySQL Workbench and run.

CREATE DATABASE IF NOT EXISTS studentdb;
USE studentdb;

CREATE TABLE IF NOT EXISTS students (
    id      INT AUTO_INCREMENT PRIMARY KEY,   -- unique id, auto-generated
    name    VARCHAR(100) NOT NULL,
    email   VARCHAR(100) NOT NULL,
    course  VARCHAR(100) NOT NULL
);

-- A few starter rows so the table isn't empty
INSERT INTO students (name, email, course) VALUES
    ('Asha Rao',   'asha@example.com',  'Computer Science'),
    ('Ravi Kumar', 'ravi@example.com',  'Mechanical'),
    ('Meera Nair', 'meera@example.com', 'Electronics');
