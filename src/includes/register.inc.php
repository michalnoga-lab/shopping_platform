<?php
// error_reporting(0); TODO

if (isset($_POST['submit'])) {

    $username = $_POST['username'];
    $email = $_POST['email'];
    $password = $_POST['password'];
    $passwordConfirmation = $_POST['passwordConfirmation'];

    include '../classes/dbh.classes.php';
    include '../classes/register.classes.php';
    include '../classes/register-controller.classes.php';

    $register = new RegisterController($username, $email, $password, $passwordConfirmation);
    $register->registerUser();

    header('location: ../index.php?info=user_added');
}