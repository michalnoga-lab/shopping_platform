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

//    public function updateCartValue($productNettPrice, $productVat, $productGrossPrice, $productQuantity): void
//    {
//        $stmt = $this->connect()->prepare('SELECT * FROM carts WHERE id = ?;');
//        $stmt->execute([$_SESSION['cart-id']]);
//        $cart = $stmt->fetch();
//        list($cartId, $userId, $purchased, $nettCartValueFromDb, $vatCartValueFromDb, $grossCartValueFromDb, $addressId, $userComment, $closed) = $cart;
//        $updatedNettValue = $nettCartValueFromDb + $productNettPrice * $productQuantity;
//        $updatedVatValue = $vatCartValueFromDb + $productNettPrice * $productVat / 100;
//        $updatedGrossValue = $grossCartValueFromDb + $productNettPrice * (1 + $productVat / 100);
//
//        $stmt = $this->connect()->prepare('UPDATE carts SET nett_value = ?, vat_value = ?, gross_value = ? WHERE id = ?;');
//        $stmt->execute(array($updatedNettValue, $updatedVatValue, $updatedGrossValue, $_SESSION['cart-id']));
//        $stmt = null;
//
//        // TODO update na podstawie danych w koszyku zrobić !!!
//    }

    public function updateCartValue()
    {
        $stmt = $this->connect()->prepare('SELECT * FROM products_in_cart where cart_id = ?;');
        $stmt->execute([$_SESSION['cart-id']]);
        $productsInCart = $stmt->fetchAll();
        $productsNettValue = 0;
        $productsVatValue = 0;
        $productsGrossValue = 0;

        foreach ($productsInCart as $product) {
            $productsNettValue = $productsNettValue + $product['nett_value'];
            $productsVatValue = $productsVatValue + $product['vat_value'];
            $productsGrossValue = $productsGrossValue + $product['gross_value'];
        }

        $stmt = $this->connect()->prepare('UPDATE carts SET nett_value = ?, vat_value = ?, gross_value = ? WHERE id = ?;');
        $stmt->execute(array($productsNettValue, $productsVatValue, $productsGrossValue, $_SESSION['cart-id']));
        $stmt = null;
    }
}