<?php
//
//include_once('includes/db_config.inc.php');
//
//if (isset($_POST['login'])) {
//    $email = $_POST['email'];
//    #$password = password_hash($_POST['password'], PASSWORD_BCRYPT, ['cost' => 12]);
//    $password = $_POST['password']; // TODO hasło hashowanie!
//    $query = mysqli_query($connection, "SELECT * FROM users WHERE email='$email' and password='$password'");
//    $result = mysqli_fetch_array($query);
//
//    if ($result > 0) {
//        $extra = 'products.php';
//        $_SESSION['email'] = $email;
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
//}
//?>

<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Platforma zakupowa dla wybranych klientów">
    <meta name="author" content="Michał Noga OBSIDO">
    <title>Platforma zakupowa | Logowanie</title>

    <link href="assets/css/bootstrap.css" rel="stylesheet">

    <link href="assets/css/styles.css" rel="stylesheet">
    <link href="assets/css/font-awesome.min.css" rel="stylesheet">
    <link href="assets/css/animate-custom.css" rel="stylesheet">
    <script src="assets/js/jquery.min.js"></script>
    <script type="text/javascript" src="assets/js/modernizr.custom.js"></script>

    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>

</head>

<body>
<div id="contact">
    <div class="container">
        <div class="row">
            <h2 class="centered">Logowanie</h2>
            <hr>
        </div>
        <script type="text/javascript">

        </script>
        <div class="row centered">
            <div class="col-10">
                <form id="login-form" method="post" class="form" role="form" action="includes/login.inc.php">
                    <div class="row">
                        <div class="col-md-12 form-group">
                            <label for="email"></label>
                            <input class="form-control" id="email" name="email" placeholder="Podaj email" type="text"
                                   required/> <!-- TODO regex -> pattern-->
                        </div>
                        <div class="col-md-12 form-group">
                            <label for="password"></label>
                            <input class="form-control" id="password" name="password" placeholder="Podaj hasło"
                                   type="password" required"/> <!-- TODO regex -->
                        </div>
                        <div class="col-md-12 form-group">
                            <button class="btn btn-lg" type="submit" name="submit">Logowanie</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

</body>
</html>