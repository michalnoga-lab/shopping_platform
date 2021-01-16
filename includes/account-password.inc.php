<?php
// error_reporting(0); // TODO
session_start();

if (isset($_POST['submit'])) {
    include_once '../classes/dbh.classes.php';
    $userId = $_SESSION['id'];
    $password = $_POST['password'];
    $passwordConfirmation = $_POST['passwordConfirmation'];

    if($password!==$passwordConfirmation){
        header('location: ../pages/account.php?error=different_passwords');
        exit();
    }

    $hashedPassword = password_hash($password, PASSWORD_DEFAULT);

    $sql = "UPDATE users SET password = '$hashedPassword' WHERE id='$userId'";

    if (mysqli_query($connection, $sql)) {
        header('location: ../pages/account.php?error=password_updated');
    } else {
        header('location: ../pages/account.php?error=connection');
    }
    exit();
}