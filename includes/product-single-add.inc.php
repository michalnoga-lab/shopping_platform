<?php
// error_reporting(0); // TODO enable

if (isset($_POST['submit'])) {

    $productId = $_POST['product-id'];
    $userComment = $_POST['comment'];
    $quantity = $_POST['quantity'];
    $nettPrice = $_POST['nett-price'];
    $vat = $_POST['vat'];
    $grossPrice = $_POST['gross-price'];
    $intQuantity = 0; // TODO czy ta zmienna jest potrzebna ???

    include '../classes/dbh.classes.php';
    include '../classes/cart.classes.php';
    include '../classes/product-single-add.classes.php';
    include '../classes/product-single-add.controller.classes.php';

    $cart = new Cart();
    $productToAddToCart = new ProductSingleAddController($_SESSION['id'], $_SESSION['cart-id'], $productId, $userComment, $quantity, $nettPrice, $vat, $grossPrice);
    $productToAddToCart->addProduct();
    $cart->updateCartValue($nettPrice, $vat, $grossPrice, $quantity);

    header('location: ../pages/products.php?info=product_saved');
}