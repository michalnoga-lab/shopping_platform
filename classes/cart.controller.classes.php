<?php
// error_reporting(0); TODO

class CartController extends Cart
{
    private int $userId;
    private string $purchased;
    private float $nettValue;
    private float $vatValue;
    private float $grossValue;
    private int $addressId;
    private string $userComment;
    private bool $closed;

    public function __construct($userId, $purchased, $nettValue, $vatValue, $grossValue, $addressId, $userComment, $closed)
    {
        $this->userId = $userId;
        $this->purchased = $purchased;
        $this->nettValue = $nettValue;
        $this->vatValue = $vatValue;
        $this->grossValue = $grossValue;
        $this->addressId = $addressId;
        $this->userComment = $userComment;
        $this->closed = $closed;
    }
}