<?php
// error_reporting(0); // TODO enable

if (isset($_POST['submit'])) {

    $productId = $_POST['product-id'];
    $quantity = $_POST['quantity'];
    $nettPrice = $_POST['nett-price'];
    $vat = $_POST['vat'];
    $grossPrice = $_POST['gross-price'];

    include '../classes/dbh.classes.php';
    include '../classes/cart.classes.php';
    include '../classes/product-single-edit.classes.php';
    include '../classes/product-single-edit.controller.classes.php';

    $cart = new Cart();
    //$productToEditInCart = new ProductSingleEditController($productId, $quantity, $nettPrice, $vat, $grossPrice);
    $productToEditInCart = new ProductSingleEditController($productId, $quantity, $nettPrice); // TODO tutaj zacząć
    //$productToEditInCart->updateProduct();
    // $cart->updateCartValue(); // TODO

    header('location: ../pages/cart.php?info=product_updated');
}