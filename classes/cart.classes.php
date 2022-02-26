<?php
// error_reporting(0); TODO

include_once '../includes/logger.inc.php';

class Cart extends Dbh
{
    protected function userHasCart(): bool
    {
        $stmt = $this->connect()->prepare('SELECT * FROM carts WHERE user_id = ? AND closed = false;');
        $stmt->execute(array($_SESSION['id']));
        $array = $stmt->fetch();

        if ($array) {
            list($cartId) = $array;
            $_SESSION['cart-id'] = $cartId;
            $stmt = null;
            return true;
        }
        $stmt = null;
        return false;
    }

    public function createEmptyCart(): void
    {
        if (!$this->userHasCart()) {
            $stmt = $this->connect()->prepare('INSERT INTO  carts (user_id, purchased, nett_value, vat_value, gross_value, address_id, user_comment, closed) 
                                                VALUES (?, ?, ?, ?, ?, ?, ?, ?);');

            if ($stmt->execute(array($_SESSION['id'], null, 0, 0, 0, 0, null, false))) {
                $cartId = $this->connect()->lastInsertId();
                $_SESSION['cart-id'] = $cartId;
            } else {
                header('location: ../pages/products.php?info=cart_error');
            }
            $stmt = null;
        }
    }

    public function updateCartValue($productNettPrice, $productVat, $productGrossPrice, $productQuantity): void
    {
        $stmt = $this->connect()->prepare('SELECT * FROM carts WHERE id = ?;');
        $stmt->execute([$_SESSION['cart-id']]);
        $cart = $stmt->fetch();
        list($cartId, $userId, $purchased, $nettValueFromDb, $vatValueFromDb, $grossValueFromDb, $addressId, $userComment, $closed) = $cart;
        $updatedNettValue = $nettValueFromDb + $productNettPrice * $productQuantity;
        $updatedVatValue = $vatValueFromDb + $productNettPrice * $productVat / 100;
        $updatedGrossValue = $grossValueFromDb + $productNettPrice * (1 + $productVat / 100);

        $stmt = $this->connect()->prepare('UPDATE carts SET nett_value = ?, vat_value = ?, gross_value = ? WHERE id = ?;');
        $stmt->execute(array($updatedNettValue, $updatedVatValue, $updatedGrossValue, $_SESSION['cart-id']));
        $stmt = null;

        // TODO update na podstawie danych w koszyku zrobić !!!
    }
}