<?php
// error_reporting(0); // TODO
session_start();

if (isset($_POST['submit'])) {

    $street = $_POST['street'];
    $phone = $_POST['phone'];

    include '../classes/dbh.classes.php';
    include '../classes/address-add.classes.php';
    include '../classes/address-add-controller.classes.php';

    $address = new AddAddressController($street, $phone);
    $address->addAddress();

    header('location: ../pages/addresses.php?error=address_added');
}