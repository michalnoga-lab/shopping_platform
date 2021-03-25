<?php
// error_reporting(0); // TODO
session_start();

if (isset($_POST['submit'])) {
    include_once '../classes/dbh.classes.php';
    $id = $_POST['address'];

    $stmt = $connection->prepare('DELETE FROM addresses WHERE id = ?;');
    $stmt->bind_param('i', $id);

    if ($stmt->execute()) {
        header('location: ../pages/addresses.php?info=removed');
    } else {
        header('location: ../pages/addresses.php?info=connection');
    }
    exit();
}