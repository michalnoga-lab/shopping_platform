<?php
// error_reporting(0);

function emptyInputEmail($email)
{
    $result = true;

    if (!empty($email)) {
        $result = false;
    }
    return $result;
}

function emptyInputPassword($password)
{
    $result = true;

//    if (!empty($password)) {
//        $result = false;
//    }
    if (strlen($password) > 0) {
        $result = false;
    }
    return $result;
}

function invalidEmail($email)
{
    $result = true;

    if (preg_match("/^[a-z0-9\._]+@[a-z0-9\.]+\.[a-z0-9]+$/", $email)) {
        $result = false;
    }
    return $result;
}

function invalidPassword($password)
{
    $result = true;

    if (preg_match("/^.{8,200}$/", $password)) {
        $result = false;
    }
    return $result;
}

function emailToLong($email)
{
    $result = true;

    if (strlen($email) < 240) {
        $result = false;
    }
    return $result;
}

function passwordToShort($password)
{
    $result = true;

    if (strlen($password) > 7) {
        $result = false;
    }
    return $result;
}

function passwordToLong($password)
{
    $result = true;

    if (strlen($password) <= 200) {
        $result = false;
    }
    return $result;
}

function loginFailed($connection, $email, $password)
{
    $result = true;

    // TODO login validation

    return $result;
}