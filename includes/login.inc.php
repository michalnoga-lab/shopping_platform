<?php

if (isset($_POST['submit'])) {
    echo "works!";
} else {
    header("location: ../login.php");
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
//        $extra = 'login.php';
//        $user_ip = $_SERVER['REMOTE_ADDR'];
//        $query = mysqli_query($connection, "INSERT INTO userlog(email,ip,event) VALUES ('$email','$user_ip',2)");
//        $host = $_SERVER['HTTP_HOST'];
//        $uri = rtrim(dirname($_SERVER['PHP_SELF']), '/\\');
//        header("location:http://$host$uri/$extra");
//        $_SESSION['errmsg'] = 'Błędny login lub hasło';
//        exit();
//    }
//};