<?php
// error_reporting(0); TODO

include_once '../includes/logger.inc.php';

class Cart extends Dbh
{
    protected function userHasCart($userId): int
    {
        $stmt = $this->connect()->prepare('SELECT * FROM carts WHERE user_id = ? AND closed = false;');
        $stmt->execute([$userId]);
        $array = $stmt->fetch();

        if (sizeof($array) > 0) {
            // TODO czy z array można usunąć pozostałe zmienne ???
            list($cartId, $userIdFromDb, $purchased, $nettValue, $vatValue, $grossValue, $addressId, $userComment, $closed) = $array;
            $_SESSION['cart-id'] = $cartId;
            $stmt = null;
            return $cartId;
        }

        $stmt = null;
        return 0;
    }

    public function createEmptyCart($userId): int
    {
        if (!$this->userHasCart($userId)>0) {
            $stmt = $this->connect()->prepare('INSERT INTO  carts (user_id, purchased, nett_value, vat_value, gross_value, address_id, user_comment, closed) 
                                                VALUES (?, ?, ?, ?, ?, ?, ?, ?);');

            if ($stmt->execute(array($userId, null, 0, 0, 0, 0, null, false))) {
                $cartId = $this->connect()->lastInsertId();
                $_SESSION['cart-id'] = $cartId;
                return $cartId;
            } else {
                header('location ../pages/products.php?info=cart_error');
            }
            $stmt = null;
        }
        return $_SESSION['cart-id'];
    }

    protected function createEmptyCartIfNecessary($userId): void
    {



        if (!$this->userHasCart($userId) > 0) {



            $this->createEmptyCart($userId);
        }

    }
}