<?php
// error_reporting(0); TODO

class ProductSingleAdd extends Dbh
{
    protected function saveProduct($userId, $cartId, $productId, $userComment, $quantity, $nettValue, $vatValue, $grossValue): void
    {
        $stmt = $this->connect()->prepare('INSERT INTO products_in_cart (user_id, cart_id, product_id, user_comment, quantity, 
                              nett_value, vat_value, gross_value) VALUES (?, ?, ?, ?, ?, ?, ?, ?);');
        if ($stmt->execute(array($userId, $cartId, $productId, $userComment, $quantity, $nettValue, $vatValue, $grossValue))) {
            header('location ../pages/products.php?info=product_saved');
        } else {
            header('location ../pages/products.php?info=product_error');
        }
        $stmt = null;
        exit();
    }
}