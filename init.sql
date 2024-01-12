-- init.sql
DROP DATABASE IF EXISTS medicalpatient;

CREATE DATABASE medicalpatient;

USE medicalpatient;

-- Création de la table 'address'
CREATE TABLE `address` (
  `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `address` VARCHAR(100)
);

-- Création de la table 'patient'
CREATE TABLE `patient` (
  `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `last_name` VARCHAR(50) NOT NULL,
  `first_name` VARCHAR(50) NOT NULL,
  `birthday` DATE NOT NULL,
  `gender` CHAR(1) NOT NULL,
  `address_id` BIGINT,
  `phone_number` VARCHAR(15),
  CONSTRAINT `fk_patient_address` FOREIGN KEY (`address_id`) REFERENCES `address`(`id`)
);

-- Ajout de la clé étrangère après la création de la table 'patient'
ALTER TABLE `patient`
  ADD CONSTRAINT `fk_address_patient` FOREIGN KEY (`address_id`) REFERENCES `address`(`id`);

-- Insertion des données dans la table 'address'
INSERT INTO `address` (`address`)
VALUES
    ('1 Brookside St'),
    ('2 High St'),
    ('3 Club Road'),
    ('4 Valley Dr');

-- Insertion des données dans la table 'patient'
INSERT INTO `patient` (`last_name`, `first_name`, `birthday`, `gender`, `address_id`, `phone_number`)
VALUES
    ('TestNone', 'Test', '1966-12-31', 'F', 1, '100-222-3333'),
    ('TestBorderline', 'Test', '1945-06-24', 'M', 2, '200-333-4444'),
    ('TestInDanger', 'Test', '2004-06-18', 'M', 3, '300-444-5555'),
    ('TestEarlyOnset', 'Test', '2002-06-28', 'F', 4, '400-555-6666');



