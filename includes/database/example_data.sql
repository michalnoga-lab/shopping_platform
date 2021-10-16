# DROP DATABASE IF EXISTS platform;
#
# CREATE DATABASE platform;
#
# # SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
# # SET AUTOCOMMIT = 0;
# # START TRANSACTION;
# # SET time_zone = "+00:00";
#
# CREATE TABLE `users`
# (
#     `id`       int(11) AUTO_INCREMENT PRIMARY KEY NOT NULL,
#     `username` varchar(255)                       NOT NULL,
#     `email`    varchar(255)                       NOT NULL,
#     `password` varchar(255)                       NOT NULL,
#     `role`     varchar(255)                       NOT NULL,
#     `created`  timestamp                          NOT NULL DEFAULT CURRENT_TIMESTAMP,
#     `updated`  timestamp                          NOT NULL DEFAULT CURRENT_TIMESTAMP
# );
#
# CREATE TABLE `userlog`
# (
#     `id`     int(11) AUTO_INCREMENT PRIMARY KEY NOT NULL,
#     `email`  varchar(255) DEFAULT NULL,
#     `ip`     varchar(255) DEFAULT NULL,
#     `login`  timestamp    DEFAULT CURRENT_TIMESTAMP,
#     `logout` varchar(255) DEFAULT NULL
# );
#
# INSERT INTO users (username, email, password, role, created, updated)
# VALUES ('user', 'user@gmail.com', '', 'user', localtimestamp, '');
# TODO remove comment


DROP DATABASE IF EXISTS platform;

CREATE DATABASE platform;

# SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
# SET AUTOCOMMIT = 0;
# START TRANSACTION;
# SET time_zone = "+00:00";

CREATE TABLE `users`
(
    `id`       int(11) AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `username` varchar(255)                       NOT NULL,
    `email`    varchar(255)                       NOT NULL,
    `password` varchar(255)                       NOT NULL,
    `role`     varchar(255)                       NOT NULL,
    `created`  timestamp                          NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated`  timestamp                          NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `userlog`
(
    `id`      int(11) AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `user_id` int(11)      DEFAULT NULL,
    `ip`      varchar(255) DEFAULT NULL,
    `login`   timestamp    DEFAULT CURRENT_TIMESTAMP,
    `logout`  varchar(255) DEFAULT NULL,
    `event`   int(11)      DEFAULT NULL
);

INSERT INTO users (username, email, password, role, created, updated)
VALUES ('user', 'user@gmail.com', '', 'user', localtimestamp, localtimestamp);