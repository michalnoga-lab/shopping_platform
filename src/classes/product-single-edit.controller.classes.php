<?php
// error_reporting(0); TODO enable

class ProductSingleEditController extends ProductSingleEdit
{
    private int $productId;
    private int $quantity;
    private float $nettPrice;
    private int $vat;
    private float $grossPrice;

    public function __construct(int $productId, int $quantity, string $nettPrice, string $vat, string $grossPrice)
    {
        $this->productId = $productId;
        $this->quantity = $quantity;
        $this->nettPrice = floatval($nettPrice);
        $this->vat = intval($vat);
        $this->grossPrice = floatval($grossPrice);
    }

    public function updateProduct(): void
    {
        try {
            if ($this->quantity <= 0) {
                throw new Exception('Invalid quantity');
            }
        } catch (Exception $e) {
            header('location: ../pages/cart.php?info=product_error');
            exit();
        }

        $nettValue = $this->quantity * $this->nettPrice;
        $vatValue = $nettValue * $this->vat / 100;
        $grossValue = $nettValue + $vatValue;

        $this->updateQuantity($this->productId, $this->quantity, $nettValue, $vatValue, $grossValue);
    }
}