DROP DATABASE IF EXISTS platform;

CREATE DATABASE platform;

CREATE TABLE `users`
(
    `id`       int(11) AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `username` varchar(255)                       NOT NULL,
    `email`    varchar(255)                       NOT NULL,
    `password` varchar(255)                       NOT NULL,
    `role`     varchar(255)                       NOT NULL
) CHARACTER SET utf8
  COLLATE utf8_general_ci;

CREATE TABLE `products`
(
    `id`             int(11) AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `auction_number` varchar(255)                       NOT NULL,
    `name`           varchar(255)                       NOT NULL,
    `description`    varchar(255)                       NOT NULL,
    `nett_price`     decimal(10, 2)                     NOT NULL,
    `vat`            int(11)                            NOT NULL,
    `gross_price`    decimal(10, 2)                     NOT NULL,
    `optima_code`    varchar(255),
    `ean`            varchar(255)
) CHARACTER SET utf8
  COLLATE utf8_general_ci;

INSERT INTO products (auction_number, name, description, nett_price, vat, gross_price, optima_code, ean)
VALUES ('1a', 'Product 1', 'Product description 1', 10.00, 23, 12.30, 'code1', 123456);

INSERT INTO products (auction_number, name, description, nett_price, vat, gross_price, optima_code, ean)
VALUES ('2b', 'Product 2', 'Product description 2', 100.00, 23, 123.00, 'code2', 67890);

# INSERT INTO users (username, email, password, role)
# VALUES ('user', 'user@gmail.com', '$2y$10$DgPcQ1AGo..ZOR1b1w/KKup0U7/KFXJtjSAM5w3WT.hLGyJdeFp4i',
#         'user'); # password = user user