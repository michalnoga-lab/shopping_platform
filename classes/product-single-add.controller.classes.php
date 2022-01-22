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
        $nettValue = $this->quantity * $this->nettPrice;
        $vatValue = $nettValue * $this->vat;
        $grossValue = $nettValue + $vatValue;

        $this->saveProduct($this->userId, $this->cartId, $this->productId, $this->userComment, $this->quantity, $nettValue, $vatValue, $grossValue);
    }
}