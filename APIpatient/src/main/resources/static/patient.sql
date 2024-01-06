DROP DATABASE medicalpatient;

CREATE DATABASE medicalpatient;

USE medicalpatient;


CREATE TABLE `patient` (
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `last_name` VARCHAR(50) NOT NULL,
    `first_name` VARCHAR(50) NOT NULL,
    `birthday` DATE NOT NULL,
    `gender` CHAR(1) NOT NULL,
    `address` VARCHAR(100),
    `phone_number` VARCHAR(15)
);

INSERT INTO `patient` (`last_name`, `first_name`, `birthday`, `gender`, `address`, `phone_number`)
VALUES
    ('TestNone', 'Test', '1966-12-31', 'F', '1 Brookside St', '100-222-3333'),
    ('TestBorderline', 'Test', '1945-06-24', 'M', '2 High St', '200-333-4444'),
    ('TestInDanger', 'Test', '2004-06-18', 'M', '3 Club Road', '300-444-5555'),
    ('TestEarlyOnset', 'Test', '2002-06-28', 'F', '4 Valley Dr', '400-555-6666');






CREATE TABLE `patient` (
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `last_name` VARCHAR(50) NOT NULL,
    `first_name` VARCHAR(50) NOT NULL,
    `birthday` DATE NOT NULL,
    `gender` CHAR(1) NOT NULL
);

CREATE TABLE `contact` (
    `patient_id` INT NOT NULL,
    `address` VARCHAR(100) NOT NULL,
    `phone_number` VARCHAR(15) NOT NULL,

    FOREIGN KEY (`patient_id`) REFERENCES `patient` (`id`)
);
