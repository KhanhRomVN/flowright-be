CREATE TABLE `flowright`.`users` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(50) NOT NULL UNIQUE,
    `email` VARCHAR(100) NOT NULL UNIQUE,
    `password` VARCHAR(255) NOT NULL,
    `refresh_token` VARCHAR(255),
    PRIMARY KEY (`id`)
);