<?php
// error_reporting(0); // TODO
session_start();

if (isset($_POST['submit'])) {
    include_once '../classes/dbh.classes.php';
    $addressId = $_POST['address'];

    $sql = "DELETE FROM addresses WHERE id = '$addressId';";

    if ($result = mysqli_query($connection, $sql)) {
        header('location: ../pages/addresses.php?error=removed');
    } else {
        header('location: ../pages/addresses.php?error=connection');
    }
    exit();
}