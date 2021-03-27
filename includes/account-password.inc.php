<?php
// error_reporting(0); // TODO
session_start();

if (isset($_POST['submit'])) {
    include_once '../classes/dbh.classes.php';
    $id = $_SESSION['id'];
    $password = $_POST['password'];
    $passwordConfirmation = $_POST['passwordConfirmation'];
    $location = 'location: ../pages/account.php?info=';

    if ($password !== $passwordConfirmation) {
        header($location . 'different_passwords');
        exit();
    }
    $hashedPassword = password_hash($password, PASSWORD_DEFAULT);

    $stmt = $connection->prepare('UPDATE users SET password = ? WHERE id = ?');
    $stmt->bind_param('si', $hashedPassword, $id);

    if ($stmt->execute()) {
        header($location . 'password_updated');
    } else {
        header($location . 'connection');
    }
    exit();
}