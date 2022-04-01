<?php
// error_reporting(0); TODO
session_start();

?>

<!doctype html>
<html lang="pl">
<head>
    <?php include_once('../static/elements/head.inc.php'); ?>
    <title>Prima Platforma</title>
</head>

<body>

<header>
    <?php include_once('../static/elements/header.php') ?>
</header>

<div class="container">
    <?php
    if (isset($_GET['info'])) {
        $infoCheck = $_GET['info'];

        if ($infoCheck === 'name_updated') {
            echo('<div class="alert alert-success text-center" role="alert">Nazwa użytkownika zaktualizowana</div>');
        } elseif ($infoCheck === 'password_updated') {
            echo('<div class="alert alert-success text-center" role="alert">Hasło zostało zaktualizowane</div>');
        } elseif ($infoCheck === 'different_passwords') {
            echo('<div class="alert alert-danger text-center" role="alert">Hasło i potwierdzenie hasła nie są takie same</div>');
        } elseif ($infoCheck === 'invalid_name') {
            echo('<div class="alert alert-danger text-center" role="alert">Dozwolone są tylko litery, cyfry i spacja</div>');
        } elseif ($infoCheck === 'short_name') {
            echo('<div class="alert alert-danger text-center" role="alert">Minimalna długość nazwy użytkownika to 1 znak</div>');
        } elseif ($infoCheck === 'long_name') {
            echo('<div class="alert alert-danger text-center" role="alert">Maksymalna długość nazwy użytkownika to 200 znaków</div>');
        } elseif ($infoCheck === 'short_password') {
            echo('<div class="alert alert-danger text-center" role="alert">Minimalna długość hasła to 8 znaków</div>');
        } elseif ($infoCheck === 'long_password') {
            echo('<div class="alert alert-danger text-center" role="alert">Maksymalna długość hasła to 200 znaków</div>');
        } elseif ($infoCheck === 'connection') {
            echo('<div class="alert alert-danger text-center" role="alert">Błąd połączenia</div>');
        }
    }
    ?>
    <div class="row">
        <h3>Moje konto</h3>
        <hr>
        <?php
        include_once '../classes/dbh.classes.php';

        $stmt = $connection->prepare('SELECT * from users WHERE id= ?;');
        $stmt->bind_param('i', $_SESSION['id']);
        $stmt->execute();
        $result = $stmt->get_result();

        if ($result > 0) {
            $user = $result->fetch_assoc(); ?>
            <p>Nazwa użytkownika: <?= $user['username'] ?></p>
            <p>Email: <?= $user['email'] ?></p>
            <hr>
            <button class="btn btn-primary btn block mb-2" onclick="window.location='account-name.php'">Zmień nazwę
            </button>
            <button class="btn btn-primary btn-block" onclick="window.location='account-password.php'">Zmień hasło
            </button>
            <?php
        } else {
            echo('<div class="alert alert-danger text-center" role="alert">Nie odnaleziono danych użytkownika</div>');
        }
        ?>
    </div>
</div>

<footer>
    <?php include_once('../static/elements/footer.php') ?>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous">
</script>

</body>
</html>