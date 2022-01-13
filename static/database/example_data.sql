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

CREATE TABLE `products`
(
    `id`             int(11) AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `auction_number` varchar(255)                       NOT NULL,
    `name`           varchar(255)                       NOT NULL,
    `description`    varchar(1000)                      NOT NULL,
    `nett_price`     decimal(10, 2)                     NOT NULL,
    `vat`            int(11)                            NOT NULL,
    `gross_price`    decimal(10, 2)                     NOT NULL,
    `optima_code`    varchar(255),
    `ean`            varchar(255)
);

CREATE TABLE addresses
(
    `id`      int(11) AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `user_id` int(11)                            NOT NULL,
    `street`  varchar(255)                       NOT NULL,
    `phone`   varchar(255)                       NOT NULL
);

CREATE TABLE company
(
    `id`            int(11) AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `name`          varchar(255)                       NOT NULL,
    `nip`           varchar(255)                       NOT NULL,
    `street`        varchar(255)                       NOT NULL,
    `street_number` varchar(255)                       NOT NULL,
    `city`          varchar(255)                       NOT NULL,
    `post_code`     varchar(255)                       NOT NULL,
    `default_price` varchar(255)                       NOT NULL
);

CREATE TABLE carts
(
    `id`           int(11) AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `user_id`      int(11)                            NOT NULL,
    `purchased`    datetime,
    `nett_value`   decimal(10, 2)                     NOT NULL,
    `vat_value`    decimal(10, 2)                     NOT NULL,
    `gross_value`  decimal(10, 2)                     NOT NULL,
    `address_id`   int(11),
    `user_comment` varchar(255),
    `closed`       boolean DEFAULT false              NOT NULL
);

CREATE TABLE products_in_cart
(
    `id`           int(11) AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `user_id`      int(11)                            NOT NULL,
    `cart_id`      int(11)                            NOT NULL,
    `product_id`   int(11)                            NOT NULL,
    `user_comment` varchar(255)                       NOT NULL,
    `quantity`     int(11)                            NOT NULL,
    `nett_value`   decimal(10, 2)                     NOT NULL,
    `vat_value`    decimal(10, 2)                     NOT NULL,
    `gross_value`  decimal(10, 2)                     NOT NULL
);

INSERT INTO users (id, username, email, password, role)
VALUES (1, 'user', 'user@gmail.com', '$2y$10$DgPcQ1AGo..ZOR1b1w/KKup0U7/KFXJtjSAM5w3WT.hLGyJdeFp4i',
        'user'); # password = user user

INSERT INTO products (id, auction_number, name, description, nett_price, vat, gross_price, optima_code, ean)
VALUES (2, '1a', 'Product 1',
        'Product description 1 Product description 1 Product description 1 Product description 1',
        10.00, 23, 12.30, 'code1', '123456');

INSERT INTO products (id, auction_number, name, description, nett_price, vat, gross_price, optima_code, ean)
VALUES (3, '2b', 'Product 2', 'Product description 2', 100.00, 23, 123.00, 'code2', 67890);

INSERT INTO products (id, auction_number, name, description, nett_price, vat, gross_price, optima_code, ean)
VALUES (4, '3c', 'Product 3', 'Product description 3', 100.00, 23, 123.00, 'code2', 67890);

INSERT INTO products (id, auction_number, name, description, nett_price, vat, gross_price, optima_code, ean)
VALUES (5, '4d', 'Product 4', 'Product description 4', 100.00, 23, 123.00, 'code2', 67890);

INSERT INTO addresses (id, user_id, street, phone)
VALUES (6, 1, 'Street 1', '123456');

INSERT INTO addresses (id, user_id, street, phone)
VALUES (7, 1, 'Street 2', '123456');

INSERT INTO addresses (user_id, street, phone)
VALUES (2, 'Street for user 2', '123456');

INSERT INTO carts (id, user_id, purchased, nett_value, vat_value, gross_value, address_id, user_comment,
                   closed)
VALUES (8, 1, null, 100, 23, 123, 6, 'comment', 0);

INSERT INTO products_in_cart (id, user_id, cart_id, product_id, user_comment, quantity, nett_value, vat_value,
                              gross_value)
VALUES (10, 1, 8, 2, 'comment to product posted by user', 100, 200, 20, 220);

INSERT INTO products_in_cart (id, user_id, cart_id, product_id, user_comment, quantity, nett_value, vat_value,
                              gross_value)
VALUES (11, 1, 8, 3, 'no user comment', 100, 200, 20, 220);