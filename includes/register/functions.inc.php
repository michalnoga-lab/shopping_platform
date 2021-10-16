<?php
// error_reporting(0); TODO enable

function emptyInput($name, $email, $password, $passwordConfirmation)
{
    if (empty($name) || empty($email) || empty($password) || empty($passwordConfirmation)) {
        return true;
    }
    return false;
}

function invalidUsername($name)
{
    if (preg_match('/^[\s\p{L}]+$/', $name)) {
        return false;
    }
    return true;
}

function invalidEmail($email)
{
    if (filter_var($email, FILTER_VALIDATE_EMAIL)) {
        return false;
    }
    return true;
}

function invalidPassword($password)
{
    if (preg_match('/^.{8,200}$/', $password)) {
        return false;
    }
    return true;
}

function passwordsMatch($password, $passwordConfirmation)
{
    if ($password === $passwordConfirmation) {
        return false;
    }
    return true;
}

function emailExists($connection, $email)
{
    $sql = "SELECT * FROM users WHERE username = ?;";
    $statement = mysqli_stmt_init($connection);

    if (!mysqli_stmt_prepare($statement, $sql)) {
        header('location: http://' . $_SERVER["HTTP_HOST"] . 'index.php?error=db_connection_failed'); // TODO https
        exit();
    }
    mysqli_stmt_bind_param($statement, 's', $email);
    mysqli_stmt_execute($statement);
    $queryResult = mysqli_stmt_get_result($statement);

    if (mysqli_fetch_assoc($queryResult)) {
        $result = true;
    } else {
        $result = false;
    }
    mysqli_stmt_close($statement);
    return $result;
}

function createUser($connection, $name, $email, $password)
{
// TODO
}