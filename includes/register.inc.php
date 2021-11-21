<?php
// error_reporting(0); TODO

if (isset($_POST['submit'])) {

    $email = $_POST['email'];
    $password = $_POST['password'];
    $passwordConfirmation = $_POST['passwordConfirmation'];

    include '../classes/register.classes.php';
    $register = new RegisterController($email, $password, $passwordConfirmation);



}