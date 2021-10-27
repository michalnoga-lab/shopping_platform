<?php
// error_reporting(0); // TODO

function emptyInput($email, $password)
{
    if (empty($email) || empty($password)) {
        return true;
    }
    return false;
}