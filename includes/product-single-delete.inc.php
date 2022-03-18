<?php
// error_reporting(0); // TODO

if (isset($_POST['submit'])) {
    $productId = $_POST['product-id'];

    include '../classes/dbh.classes.php';
    include '../classes/cart.classes.php';
    include '../classes/product-single-delete.classes.php';

    $cart = new Cart();
    $productToDelete = new ProductSingleDelete();
    $productToDelete->deleteProductFromCart($productId);
    $cart->updateCartValue();

    header('location: ../pages/cart.php?info=product_deleted');
}