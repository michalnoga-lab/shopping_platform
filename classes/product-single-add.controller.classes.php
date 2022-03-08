<?php
// error_reporting(0); TODO

class ProductSingleAddController extends ProductSingleAdd
{
    private int $userId;
    private int $cartId;
    private int $productId;
    private string $userComment;
    private int $quantity;
    private float $nettPrice;
    private float $vat;
    private float $grossPrice;

    public function __construct(int $userId, int $cartId, int $productId, string $userComment, int $quantity, float $nettPrice, float $vat, float $grossPrice)
    {
        $this->userId = $userId;
        $this->cartId = $cartId;
        $this->productId = $productId;
        $this->userComment = $userComment;
        $this->quantity = $quantity;
        $this->nettPrice = $nettPrice;
        $this->vat = $vat;
        $this->grossPrice = $grossPrice;
    }

    public function addProduct(): void
    {
        try {
            if ($this->quantity <= 0) {
                throw new Exception('Invalid quantity');
            }
        } catch (Exception $e) {
            header("location: ../pages/products.php?info=quantity");
            exit();
        }
        $this->saveProduct($this->userId, $this->cartId, $this->productId, $this->userComment, $this->quantity, $this->nettPrice, $this->vat, $this->grossPrice);
    }
}