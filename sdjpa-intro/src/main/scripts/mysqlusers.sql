-- I'm using bookdbuser as admin

DROP DATABASE IF EXISTS bookdb;

DROP USER IF EXISTS `bookdbadmin`@`%`;

DROP USER IF EXISTS `bookdbuser`@`%`;

CREATE DATABASE IF NOT EXISTS bookdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE USER IF NOT EXISTS `bookdbadmin`@`%` IDENTIFIED WITH mysql_native_password BY 'password';
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, REFERENCES, INDEX, ALTER, EXECUTE, CREATE VIEW, SHOW VIEW,
CREATE ROUTINE, ALTER ROUTINE, EVENT, TRIGGER ON `bookdb`.* TO `bookdbadmin`@`%`;

CREATE USER IF NOT EXISTS `bookdbuser`@`%` IDENTIFIED WITH mysql_native_password BY 'password';
GRANT SELECT, INSERT, UPDATE, DELETE, SHOW VIEW ON `bookdb`.* TO `bookdbuser`@`%`;
FLUSH PRIVILEGES;