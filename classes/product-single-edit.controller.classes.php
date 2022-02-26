<?php
// error_reporting(0); TODO enable

class ProductSingleEditController
//class ProductSingleEditController extends ProductSingleEdit
{
//    private int $quantity;
//    private int $productInCartId;
//    private string $nettPrice;
//    private float $nettPrice;
    //private int $vat;
//    private float $grossPrice;
//    private int $productInCartId;

    //public function __construct(int $quantity, float $nettPrice, int $vat, float $grossPrice, int $productInCartId)
    //public function __construct(int $productInCartId, int $quantity, int $vat)
    //{
    //    $this->quantity = $quantity;
    //   $this->productInCartId = $productInCartId;
    //$this->nettPrice = $nettPrice;
//        $this->nettPrice = $nettPrice;
    //$this->vat = $vat;
//        $this->grossPrice = $grossPrice;
//        $this->productInCartId = $productInCartId;
    //}

    private int $productId;
    private int $quantity;
    private float $nettPrice;
//    private float $vat;
//    private float $grossPrice;

    public function __construct(int $productId, int $quantity, float $nettPrice)
    {
        $this->productId = $productId;
        $this->quantity = $quantity;
        $this->nettPrice = $nettPrice;
    }



//    public function __construct(int $productInCartId, int $quantity, float $nettPrice, float $vat, float $grossPrice)
//    {
//        $this->productInCartId = $productInCartId;
//        $this->quantity = $quantity;
//        $this->nettPrice = $nettPrice;
//        $this->vat = $vat;
//        $this->grossPrice = $grossPrice;
//    }


//    public function updateProduct(): void
//    {
//        // TODO walidacja
//
//        $nettValue = $this->quantity * $this->nettPrice;
//        $vatValue = $nettValue * $this->vat / 100;
//        $grossValue = $nettValue + $vatValue;
//
//        $this->updateQuantity($this->productInCartId, $this->quantity, $nettValue, $vatValue, $grossValue);
//    }

}