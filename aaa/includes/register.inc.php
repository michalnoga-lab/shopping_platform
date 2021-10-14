<?php
//error_reporting(0);
// TODO zrobić sesję


if (isset($_POST['submit'])) {

}


/*
 * if (isset($_POST['submit'])) {
    $email = $_POST['email'];
    $password = $_POST['password'];

    require_once("db_config.inc.php");
    require_once("functions.inc.php");

    $host = $_SERVER['HTTP_HOST'];
    // $uri = rtrim(dirname($_SERVER['PHP_SELF']), "/\\");
    // TODO kasujemy uri ???
    $location = "location: http://" . $host . "/login.php?error=";
    # TODO zrobić wszystkie header na HTTPS

    if (emptyInputEmail($email) !== false) {
        header($location . "empty_email");
        exit();
    }

    if (emptyInputPassword($password) !== false) {
        header($location . "empty_password");
        exit();
    }

    if (invalidEmail($email) !== false) {
        header($location . "invalid_email");
        exit();
    }

    if (invalidPassword($password) !== false) {
        header($location . "invalid_password");
        exit();
    }

    if (emailToLong($email) !== false) {
        header($location . "email_to_long");
        exit();
    }

    if (passwordToShort($password) !== false) {
        header($location . "password_to_short");
        exit();
    }

    if (passwordToLong($password) !== false) {
        header($location . "password_to_long");
        exit();
    }

    if (loginFailed($connection, $email, $password) !== false) {
        header($location . "login_failed");
        exit();
    }

} else {
    header("location: ../login.php");
    exit();
}
 *
 * */