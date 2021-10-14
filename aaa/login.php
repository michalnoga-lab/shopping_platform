<?php
// error_reporting(0);
// TODO wyłączyć raportowanie błędów - wszystkie strony !!!

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
    <title>Platforma zakupowa | Logowanie</title>
    <?php include_once('html_head.inc.php'); ?>
</head>

<body>
<?php include_once("elements/header.php") ?>

<div id="contact">
    <div class="container">
        <div class="row">
            <h2 class="centered">Logowanie</h2>
            <hr>
        </div>
        <div class="row centered">
            <div class="col-10">
                <form id="login-form" method="post" class="form" role="form" action="includes/login.inc.php">
                    <div class="row">
                        <div class="col-md-12 form-group">
                            <label for="email"></label>
                            <input class="form-control input" id="email" name="email" placeholder="Podaj email"
                                   type="text" required maxlength="200"/> <!-- TODO regex -> pattern-->
                        </div>
                        <div class="col-md-12 form-group">
                            <label for="password"></label>
                            <input class="form-control" id="password" name="password" placeholder="Podaj hasło"
                                   type="password" required maxlength="200"/> <!-- TODO regex -->
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

<?php include_once('elements/footer.php') ?>
</body>
</html>