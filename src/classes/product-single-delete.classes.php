<?php
// error_reporting(0); // TODO

class ProductSingleDelete extends Dbh
{
    private string $location = 'location: ../pages/cart.php?info=';

    public function deleteProductFromCart($productId): void
    {
        $stmt = $this->connect()->prepare('DELETE FROM products_in_cart WHERE product_id = ?;');

        if (!$stmt->execute(array($productId))) {
            $stmt = null;
            header($this->location . 'product_delete_error');
            exit();
        }
        $stmt = null;
    }

}