<?php
// error_reporting(0); TODO enable
$host = $_SERVER['HTTP_HOST'];

if (isset($_POST['register-submit'])) {

    require_once('functions.inc.php');
    require_once('../database/db_config.inc.php');

    $name = $_POST['username'];
    $email = $_POST['email'];
    $password = $_POST['password'];
    $passwordConfirmation = $_POST['confirm-password'];
    $location = "location: http://" . $host . "/index.php?error=";    // TODO https

    if (emptyInput($name, $email, $password, $passwordConfirmation) !== false) {
        header($location . "empty_input");
        exit();
    }

    if (invalidUsername($name) !== false) {
        header($location . 'invalid_username');
        exit();
    }

    if (invalidEmail($email) !== false) {
        header($location . 'invalid_email');
        exit();
    }

    if (invalidPassword($password) !== false) {
        header($location . 'invalid_password');
        exit();
    }

    if (passwordsMatch($password, $passwordConfirmation) !== false) {
        header($location . 'different_passwords');
        exit();
    }

    if (emailExists($connection, $email) !== false) {
        header($location . 'email_taken');
        exit();
    }

    createUser($connection, $name, $email, $password){

    }

} else {
    header("location http://" . $host . "/index.php"); // TODO https
    exit();
}