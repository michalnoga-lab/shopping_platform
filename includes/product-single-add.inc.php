<?php
// error_reporting(0); // TODO enable

if ($_POST('submit') !== null) {

    $productId = $_POST['product-id'];
    $userComment = $_POST['comment'];
    $quantity = $_POST['quantity'];
    $nettPrice = $_POST['nett-price'];
    $vat = $_POST['vat'];
    $grossPrice = $_POST['gross-price'];

    include '../classes/dbh.classes.php';
    include '../classes/cart.classes.php';
    include '../classes/cart.controller.classes.php';
    include '../classes/product-single-add.classes.php';
    include '../classes/product-single-add.controller.classes.php';

    $cart = new CartController($_SESSION['id'], null, null, null, null, null, null, null);
    $cart->prepareCartToAddProducts();
    $productToAddToCart = new ProductSingleAddController($_SESSION['id'], $_SESSION['cart-id'], $productId, $userComment, $quantity, $nettPrice, $vat, $grossPrice);
    $productToAddToCart->addProduct();

    header('location: ../pages/products.php?info=cart_saved');
}