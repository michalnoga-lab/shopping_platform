<?php
// error_reporting(0); TODO

include_once '../includes/logger.inc.php';

class Cart extends Dbh
{
    protected function userHasCart($userId): bool
    {
        $stmt = $this->connect()->prepare('SELECT * FROM carts WHERE user_id = ? AND closed = false;');

        if (!$stmt->execute(array($userId))) {
            $stmt = null;
            header('location ../pages/products.php?info=connection');
            exit();
        }

        if ($stmt->rowCount() > 0) {
            $stmt->execute();
            $_SESSION['cart-id'] = $this->connect()->lastInsertId();
            return true;
        } else {
            return false;
        }
    }

    private function createEmptyCart($userId): void
    {
        $stmt = $this->connect()->prepare('INSERT INTO  carts (user_id, purchased, nett_value, vat_value, gross_value, address_id, user_comment, closed) 
                                                VALUES (?, ?, ?, ?, ?, ?, ?, ?);');
        if ($stmt->execute(array($userId, null, 0, 0, 0, 0, null, false))) {
            $_SESSION['cart-id'] = $this->connect()->lastInsertId();
        } else {
            header('location ../pages/products.php?info=cart_error');
        }
        $stmt = null;
    }

    protected function createEmptyCartIfNecessary($userId): void
    {
        if (!$this->userHasCart($userId)) {
            $this->createEmptyCart($userId);
        }
    }
}