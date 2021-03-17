<?php
// error_reporting(0); // TODO
session_start();

if (isset($_POST['submit'])) {
    include_once '../classes/dbh.classes.php';
    $username = $_POST['name'];
    $id = $_SESSION['id'];
    $location = 'location: ../pages/account.php?info=';

    if (strlen($username) < 1) {
        header($location . 'short_name');
        exit();
    }

    $stmt = $connection->prepare("UPDATE users SET username= ? WHERE id= ?;");
    $stmt->bind_param('si', $username, $id);

    if ($stmt->execute()) {
        header($location . 'name_updated');
    } else {
        header($location . 'connection');
    }
    exit();
}