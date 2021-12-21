<?php
// error_reporting(0); TODO

include_once '../includes/logger.inc.php';

class Cart extends Dbh
{
    private int $cartId = 0;

    protected function userHasCart($userId): bool
    {
        $stmt = $this->connect()->prepare('SELECT * FROM carts WHERE user_id = ? AND closed = false;');

        if (!$stmt->execute(array($userId))) {
            $stmt = null;
            header('location ../pages/products.php?info=connection');
            exit();
        }

        if ($stmt->rowCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    protected function createEmptyCart($userId): void
    {
        $stmt = $this->connect()->prepare('INSERT INTO  carts (user_id, purchased, nett_value, vat_value, gross_value, address_id, user_comment, closed) 
                                                VALUES (?, ?, ?, ?, ?, ?, ?, ?);');
        if ($stmt->execute(array($userId, null, 0, 0, 0, 0, null, false))) {
            $this->cartId = $this->connect()->lastInsertId();
        } else {
            header('location ../pages/products.php?info=cart_error');
        }
        $stmt = null;
    }

    protected function saveCart($userId, $productId, $quantity, $nettPrice, $vat, $grossPrice, $cartId): void
    {

        if (!$this->userHasCart($userId)) {
            $this->createEmptyCart($userId);
        }
        $stmt_product = $this->connect()->prepare('INSERT INTO products_in_cart(user_id, cart_id, product_id, user_comment, quantity, nett_value, vat_value, gross_value) 
                                                        VALUES (?, ?, ?, ?, ?, ?, ?, ?);');
        $nettValue = $nettPrice * $quantity;
        $vatValue = $nettValue * $vat;
        $grosValue = $nettValue + $vatValue;

        if ($stmt_product->execute(array($userId, $this->cartId, $productId, '', $nettValue, $vatValue, $grosValue))) {
            header('location ../pages/products.php?info=product_added');
            exit();
        }
        $stmt_product = null;
    }
}