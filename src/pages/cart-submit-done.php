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

        if ($infoCheck == 'cart_submitted') {
            echo('<div class="alert alert-success text-center" role="alert">Zamówienie poprawnie złożone</div>');
        } elseif ($infoCheck == 'submit_error') {
            echo('<div class="alert alert-danger text-center" role="alert">Błąd zapisywania zamówienia</div>');
        } elseif ($infoCheck == 'mail_error') {
            echo('<div class="alert alert-danger text-center" role="alert">Błąd przesyłania zamówienia do realizacji</div>');
        }
    }
    ?>
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