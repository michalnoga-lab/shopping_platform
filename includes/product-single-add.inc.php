<?php
// error_reporting(0); // TODO enable

if ($_POST('submit') !== null) {

    $productId = $_POST['product-id'];
    $quantity = $_POST['quantity'];
    $nettPrice = $_POST['nett-price'];
    $vat = $_POST['vat'];
    $grossPrice = $_POST['gross-price'];

    include '../classes/dbh.classes.php';
    include '../classes/cart.classes.php';
    include '../classes/cart.controller.classes.php';

    $cart = new CartController();
    $cart->update();

    header('location: ../pages/products.php?info=cart_saved');
}