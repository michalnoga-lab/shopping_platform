<?php
// error_reporting(0); TODO enable

$host = $_SERVER['HTTP_HOST'];
$location = 'location: http://' . $host . '/index.php?error='; // TODO https

function emptyInput($name, $email, $password, $passwordConfirmation)
{
    if (empty($name) || empty($email) || empty($password) || empty($passwordConfirmation)) {
        return true;
    }
    return false;
}

function invalidUsername($name)
{
    if (preg_match('/^[\s\p{L}]{1,200}$/', $name)) {
        return false;
    }
    return true;
}

function invalidEmail($email)
{
    if (filter_var($email, FILTER_VALIDATE_EMAIL) && strlen($email) <= 200) {
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
    $sql = 'SELECT * FROM users WHERE email = ?;';
    $statement = mysqli_stmt_init($connection);

    if (!mysqli_stmt_prepare($statement, $sql)) {
        global $location;
        header($location . 'db_connection_failed');
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
    $sql = 'INSERT INTO users (username, email, password, role) VALUES (?, ?, ?, ?);';
    $statement = mysqli_stmt_init($connection);

    if (!mysqli_stmt_prepare($statement, $sql)) {
        global $location;
        header($location . 'db_connection_failed');
        exit();
    }
    $hashedPassword = password_hash($password, PASSWORD_DEFAULT);
    $role = 'user';
    mysqli_stmt_bind_param($statement, 'ssss', $name, $email, $hashedPassword, $role);
    mysqli_stmt_execute($statement);
    mysqli_stmt_close($statement);
    // todo ip i data
    // todo logs

    global $location;
    header($location . 'none');
    exit();

    // TODO logs - register from with email when
}