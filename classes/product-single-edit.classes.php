<?php
error_reporting(0);

class ProductSingleEdit extends Dbh
{
    private string $location = 'location: ../pages/cart.php?info=';

    protected function updateQuantity($productId, $quantity, $nettValue, $vatValue, $grossValue): void
    {
        $productInCartId = 8; //

        $stmt = $this->connect()->prepare('UPDATE products_in_cart SET quantity = ?, nett_value = ?, vat_value = ?, gross_value = ? WHERE id = ?;');
        //$stmt = $this->connect()->prepare('update products_in_cart set quantity=666999, nett_value=8, vat_value=18, gross_value=28 WHERE product_id = 3;');
        //$quantity = 654321;
        //$nettValue=1999;
        //$vatValue=2;
        //$grossValue=3;
        //$productId=3;
        //$stmt->bindParam('iiiii', $quantity, $nettValue, $vatValue, $grossValue, $productId);
        //$result = $stmt->execute();
//        if ($stmt->execute(array($quantity, $nettValue, $vatValue, $grossValue, $productId))) {
//
//            print_r('tak');
//            print_r($stmt->rowCount());
//            exit();
//        } else {
//            print_r('nie');
//            exit();
//        }


        // TODO działa update, usunąć zakomentowany kod
        // TODO zrobić aktualizowanie wartości koszyka przy zmianie ilości

        if ($stmt->execute(array($quantity, $nettValue, $vatValue, $grossValue, $productId))) {
            $stmt = null;
            header($this->location . 'product_updatedKKK');
            exit();
        }

        $result = $stmt->errorInfo();
        print_r($result);

        $stmt = null;
        header($this->location . 'product_errorHHH');
        exit();
    }
}