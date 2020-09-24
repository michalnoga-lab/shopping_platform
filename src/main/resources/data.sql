INSERT INTO product_codes (id, code_from_optima)
VALUES (1, 'example_optima_code');

INSERT INTO companies (id, active, city, default_price, name, name_shortcut, nip, payment_in_days, post_code, street,
                       street_number)
VALUES (2, true, 'krakow', 'NET', 'name', 'shortcut', '0000000000', 30, '00-000', 'street', 50),
       (11, true, 'krakow', 'NET', 'name', 'shortcut', '0000000000', 30, '00-000', 'street', 50);

INSERT INTO products (id, auction_index, description, gross_price, hidden, name, nett_price, number_in_auction,
                      vat, company_id, product_code_id)
VALUES (3, 22, 'description1', 13, false, 'name1', 10, 30, 23, 2, 1),
       (4, 22, 'description2', 14, false, 'name2', 11, 31, 23, 11, 1),
       (5, 22, 'description3', 15, false, 'name3', 12, 32, 23, 2, 1);

INSERT INTO users (id, email, enabled, login, name, password, role, surname, company_id)
VALUES (6, 'user@gmail.com', true, 'user@gmail.com', 'name',
        '{bcrypt}$2a$10$/HxZgKD8i8uVvtbyMcYPkeeybREyK72tEtVV25OxPvufeUSt9fFEa', 'USER', 'surname', 2),
       (7, 'admin@gmail.com', true, 'admin@gmail.com', 'name',
        '{bcrypt}$2a$10$/HxZgKD8i8uVvtbyMcYPkeeybREyK72tEtVV25OxPvufeUSt9fFEa', 'USER', 'surname', 11);

# INSERT INTO delivery_addresses (id, hidden, phone, street, user_id)
# VALUES (10, false, '1121212121', 'street_1', 6);

# INSERT INTO carts (id, cart_closed, order_number, purchase_time, total_gross_value, total_net_value, total_vat_value,
#                    delivery_address_id, user_id)
# VALUES (11, false, 'num_1', LOCALTIMESTAMP, 333, 222, 111, 10, 6),
#        (12, false, 'num_2', LOCALTIMESTAMP, 333, 222, 111, 10, 6);