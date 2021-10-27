<?php
// error_reporting(0); // TODO

$host = $_SERVER['HTTP_HOST'];

if (isset($_POST['login-submit'])) {

    $email = $_POST['email'];
    $password = $_POST['password'];

    require_once('functions.inc.php');
    require_once('../database/db_config.inc.php');

    if (emptyInput($email, $password) != false) {
        $location = "location: http://" . $host . "/index.php?error=";    // TODO https
        $host = $_SERVER['HTTP_HOST'];
        header("location: http://' . $host . '/index.php?error=login_empty_input"); // TODO https
        exit();
    }

} else {
    header('location: http://' . $host . '/index.php'); // TODO https
    exit();
}