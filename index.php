<?php
// error_reporting(0);
// TODO
session_start();

// TODO mail katywacyjny po rejestracji
// TODO licznik odwiedzin poszczególnych stron
?>

<!doctype html>
<html lang="pl">
<head>
    <?php include_once('static/elements/head.inc.php'); ?>
    <link href="/static/css/index.css" rel="stylesheet"/>
    <title>Prima Platforma</title>
</head>
<body>

<header>
    <?php include_once('static/elements/header.php') ?>
</header>

<div class="col-12">
    <?php
    if (isset($_GET['error'])) {
        $errorCheck = $_GET['error'];

        if ($errorCheck === 'user_added') {
            echo('<div class="alert alert-success text-center" role="alert">Użytkownik poprawnie dodany do bazy danych</div>');
        } elseif ($errorCheck === 'connection') {
            echo('<div class="alert alert-danger text-center" role="alert">Błąd połączenia</div>');
        } elseif ($errorCheck === 'logged_out') {
            echo('<div class="alert alert-success text-center" role="alert">Koniec pracy. Poprawne wylogowano!</div>');
        }
    }
    ?>
    <div class="h-100 p-5 bg-light border rounded-3">
        <h2>O platformie</h2>
        <p>Platforma jest dostępna tylko dla klientów przetargowych, którzy mają podpisaną z nami umowę.</p>
        <p>Coś nie działa, masz pomysł, sugestię co zmienić na platformie, potrzebujesz nowej
            funkcjonalności - napisz do nas - biuro@primakrakow.pl</p>
        <p>Kontakt telefoniczny: (12) 411 25 09, (12) 411 67 54</p>
        <p>Sprawdź naszą politykę <a href='/pages/cookies.php'>Cookies</a></p>
        <p>Sprawdź jak przetwarzamy dane <a href='/pages/rodo.php'>RODO</a></p>
    </div>
    <!-- TODO https poniżej -->
    <?php
    $url = "http://$_SERVER[HTTP_HOST]$_SERVER[REQUEST_URI]";
    if (strpos($url, 'error=empty_input') == true) {
//        echo("!!! BLAD !!!");
//        exit();

        if (!isset($_GET['error'])) {
            exit();
        } else {
            $error_message = $_GET['error'];
        }
    }
    ?>
</div>


<footer>
    <?php include_once('static/elements/footer.php') ?>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous">
</script>
</body>
</html>