<?php
// error_reporting(0); TODO

class ProductSingleAdd extends Dbh
{
    private string $location = 'location: ../pages/products.php?info=';

    protected function saveProduct($userId, $cartId, $productId, $userComment, $quantity, $nettValue, $vatValue, $grossValue): void
    {
        $stmt = $this->connect()->prepare('SELECT * FROM products_in_cart WHERE cart_id = ? AND product_id = ?;');

        if (!$stmt->execute(array($cartId, $productId))) {
            $stmt = null;
            header($this->location . 'error');
        }

        if ($stmt->rowCount() == 0) {
            $insert_stmt = $this->connect()->prepare('INSERT INTO products_in_cart (user_id, cart_id, product_id, user_comment, quantity,
                              nett_value, vat_value, gross_value) VALUES (?, ?, ?, ?, ?, ?, ?, ?);');

            if (!$insert_stmt->execute(array($userId, $cartId, $productId, $userComment, $quantity, $nettValue, $vatValue, $grossValue))) {
                $stmt = null;
                $insert_stmt = null;
                header($this->location . 'error');
                exit();
            } else {
                $product = $stmt->fetch();
                $product['quantity'] = $product['quantity'] + $quantity;

                $stmt_update = $this->connect()->prepare('UPDATE products_in_cart SET quantity = ?, nett_value = ?, vat_value = ?, gross_value = ?
                           WHERE product_id = ?;');

                if (!$stmt_update->execute(array($quantity, $nettValue, $vatValue, $grossValue, $productId))) {
                    $stmt = null;
                    $stmt_update = null;
                    header($this->location . 'error');
                    exit();
                }

                $stmt = null;
                header($this->location . 'product_updated');
                exit();
            }
        }
    }
//    {
//        $stmt = $this->connect()->prepare('SELECT * FROM products_in_cart WHERE cart_id = ? AND product_id = ?;');
////        $stmt->bindParam('ii', $cartId, $productId);
////        $stmt->execute(array($cartId, $productId));
////        $result = $stmt->fetch();
////
////        print_r(json_encode($result), JSON_PRETTY_PRINT);
////        exit();
//
//        if (!$result) {
//            $stmt = $this->connect()->prepare('INSERT INTO products_in_cart (user_id, cart_id, product_id, user_comment, quantity,
//                              nett_value, vat_value, gross_value) VALUES (?, ?, ?, ?, ?, ?, ?, ?);');
//
//            if (!$stmt->execute(array($userId, $cartId, $productId, $userComment, $quantity, $nettValue, $vatValue, $grossValue))) {
//                $stmt = null;
//                header('location: ../pages/products.php?info=product_error');
//            }
//        } else {
//            //$productFromDb = $stmt->fetch(MYSQLI_ASSOC);
//            print_r(json_encode($result), JSON_PRETTY_PRINT);
//            exit();
//        }
//        $stmt = null;
//    }
}