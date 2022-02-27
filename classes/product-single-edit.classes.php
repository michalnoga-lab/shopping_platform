<?php
error_reporting(0);

class ProductSingleEdit extends Dbh
{
    protected function updateQuantity($productId, $quantity, $nettValue, $vatValue, $grossValue): void
    {
        $stmt = $this->connect()->prepare('UPDATE products_in_cart SET quantity = ? , nett_value = ? , vat_value = ? , gross_value = ? WHERE product_id = ?;');

        if ($stmt->execute(array($quantity, $nettValue, $vatValue, $grossValue, $productId))) {
            $stmt = null;
            header('location: ../pages/cart.php?info=product_error');
        }
        $stmt = null;
    }
}