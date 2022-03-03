<?php
error_reporting(0);

class ProductSingleEdit extends Dbh
{
    private string $location = 'location: ../pages/cart.php?info=';

    protected function updateQuantity($productId, $quantity, $nettValue, $vatValue, $grossValue): void
    {
        $stmt = $this->connect()->prepare('UPDATE products_in_cart SET quantity = ?, nett_value = ?, vat_value = ?, gross_value = ? WHERE product_id = ?;');

        if (!$stmt->execute(array($quantity, $nettValue, $vatValue, $grossValue, $productId))) {
            $stmt = null;
            header($this->location . 'product_error');
            exit();
        }

        $stmt = null;
    }
}