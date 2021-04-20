<?php
// error_reporting(0); // TODO

if (isset($_POST['submit'])) {

    $email = $_POST['email'];
    $password = $_POST['password'];

    include '../classes/dbh.classes.php';
    include '../classes/login.classes.php';
    include '../classes/login-controller.classes.php';

    $login = new LoginController($email, $password);
    $login->doLogin();

    header('location: ../index.php/error=login_successful');
}