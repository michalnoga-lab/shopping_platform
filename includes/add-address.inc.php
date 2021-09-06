<?php
// error_reporting(0); // TODO

if (isset($_POST['submit'])) {

    $userId = $_SESSION['id'];
    $street = $_POST['street'];
    $phone = $_POST['phone'];

    include '../classes/dbh.classes.php';

    $address = new AddressController($userId, $street, $phone);
    $address->addAddress();

    header('location: /addresses?error=address_added');
}