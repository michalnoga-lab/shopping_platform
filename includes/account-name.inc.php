<?php
// error_reporting(0); // TODO
session_start();

if (isset($_POST['submit'])) {
    include_once '../classes/dbh.classes.php';
    $username = $_POST['name'];
    $id = $_SESSION['id'];
    $location = 'location: ../pages/account.php?info=';

    if (!preg_match('/^[a-zA-Z0-9\s_-]{0,200}$/', $username)) {
        header($location . 'invalid_name');
        exit();
    }

    if (strlen($username) < 1) {
        header($location . 'short_name');
        exit();
    }

    if (strlen($username) > 200) {
        header($location . 'long_name');
        exit();
    }

    $stmt = $connection->prepare("UPDATE users SET username= ? WHERE id= ?;");
    $stmt->bind_param('si', $username, $id);

    if ($stmt->execute()) {
        $logger = new Logger();
        $logger->systemEvent('Name changed for ' . $username);

        header($location . 'name_updated');
    } else {
        header($location . 'connection');
    }
    exit();
}