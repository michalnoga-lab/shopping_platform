<?php
// error_reporting(0); TODO

include '../classes/dbh.classes.php';
include '../classes/cart.classes.php';
include '../classes/mail.classes.php';

if (isset($_POST['address-id'])) {
    $addressId = $_POST['address-id'];

    $cart = new Cart();
    $cart->assignAddressToCart($addressId);
    $cart->closeCart();
    $mailFunction = new MailFunction();
    $mailFunction->sendEmail('biuro@primakrakow.pl', 'Zamówienie', 'Treść wiadomości', '', '');
}

header('location: ../pages/cart-submit-done.php?info=cart_submitted');