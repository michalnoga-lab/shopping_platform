DROP DATABASE IF EXISTS platform;

CREATE DATABASE platform;

CREATE TABLE `users`
(
    `id`       int(11) AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `username` varchar(255)                       NOT NULL,
    `email`    varchar(255)                       NOT NULL,
    `password` varchar(255)                       NOT NULL,
    `role`     varchar(255)                       NOT NULL
);

CREATE TABLE `user_log`
(
    `id`      int(11) AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `user_id` int(11)                                     DEFAULT NULL,
    `ip`      varchar(255)                                DEFAULT NULL,
    `time`    timestamp                          NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `event`   int(11)                                     DEFAULT NULL
);

INSERT INTO users (username, email, password, role, created, updated)
VALUES ('user', 'user@gmail.com', '$2y$10$DgPcQ1AGo..ZOR1b1w/KKup0U7/KFXJtjSAM5w3WT.hLGyJdeFp4i', 'user',
        localtimestamp, localtimestamp); # password = user user