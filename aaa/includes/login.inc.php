<?php
// error_reporting(0);

if (isset($_POST['submit'])) {

    require_once("db_config.inc.php");
    require_once("functions.inc.php");

    $email = $_POST['email'];
    $password = $_POST['password'];

    $host = $_SERVER['HTTP_HOST'];
    // $uri = rtrim(dirname($_SERVER['PHP_SELF']), "/\\");
    // TODO kasujemy uri ???
    $location = "location: http://" . $host . "/index.php?error=";
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
    header("location: ../index.php");
    exit();
}


//include_once('includes/db_config.inc.php');
//
//if (isset($_POST['login'])) {
//    $email = $_POST['email'];
//    $password = password_hash($_POST['password'], PASSWORD_BCRYPT, ['cost' => 12]);
//    $query = mysqli_query($connection, "SELECT * FROM users WHERE email='$email' and password='$password'");
//    $result = mysqli_fetch_array($query);
//
//    if ($result > 0) {
//        $extra = ''; # TODO gdzie przechodzi użytkownik po logowaniu
//        $_SESSION['login'] = $email;
//        $_SESSION['id'] = $result['id'];
//        $_SESSION['first_name'] = $result['first_name'];
//        $_SESSION['last_name'] = $result['last_name'];
//        $user_ip = $_SERVER['REMOTE_ADDR'];
//        $query = mysqli_query($connection, "INSERT INTO userlog(email, ip, event) VALUES ('$email', '$user_ip',1)");
//        $host = $_SERVER['HTTP_HOST'];
//        $uri = rtrim(dirname($_SERVER['PHP_SELF']), '/\\');
//        header("location:http://$host$uri/$extra");
//        exit();
//    } else {
//        $extra = 'index.php';
//        $user_ip = $_SERVER['REMOTE_ADDR'];
//        $query = mysqli_query($connection, "INSERT INTO userlog(email,ip,event) VALUES ('$email','$user_ip',2)");
//        $host = $_SERVER['HTTP_HOST'];
//        $uri = rtrim(dirname($_SERVER['PHP_SELF']), '/\\');
//        header("location:http://$host$uri/$extra");
//        $_SESSION['errmsg'] = 'Błędny login lub hasło';
//        exit();
//    }
//};