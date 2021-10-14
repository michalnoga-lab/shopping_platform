<?php
error_reporting(0);
$host = $_SERVER['HTTP_HOST'];

if (isset($_POST['register-submit'])) {

    require_once('includes/database/db_config.inc.php');
    require_once('includes/register/functions.inc.php');

    $name = $_POST['name'];
    $email = $_POST['email'];
    $password = $_POST['password'];
    $passwordConfirmation = $_POST['confirm-password'];
    $location = "location: http://" . $host . "/login.php?error=";

    if (emptyInput($name, $email, $password, $passwordConfirmation) !== false) {
        header($location . "KOD BLEDY !!!!!"); // TODO
        exit();
    }
} else {
    header("location http://" . $host . "/login.php");
    exit();
}