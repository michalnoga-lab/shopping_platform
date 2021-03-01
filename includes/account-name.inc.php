<?php
// error_reporting(0); // TODO
session_start();

if (isset($_POST['submit'])) {
    include_once '../classes/dbh.classes.php';
    $username = $_POST['name'];
    $id = $_SESSION['id'];

    $sql = "UPDATE users SET username='$username' WHERE id='$id';";

    if (mysqli_query($connection, $sql)) {
        header('location: ../pages/account.php?error=updated');
    } else {
        header('location: ../pages/account.php?error=connection');
    }
    exit();
}