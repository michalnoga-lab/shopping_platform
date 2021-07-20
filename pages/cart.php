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
    <div class="row">
        <h3>Mój koszyk</h3>
        <hr>
        <?php
        if (isset($_GET['info'])) {
            $infoCheck = $_GET['info'];

            if ($infoCheck === 'empty') {
                echo('<div class="alert alert-danger text-center" role="alert">Nie masz produktów w koszyku</div>');
            }
        }
        ?>
        <table class="table table-hover">
            <thead>
            <tr>
                <th scope="col" style="width: 10%">#</th>
            </tr>
            </thead>
            <tbody>
            <?php
            include_once '../classes/dbh.classes.php';

            $userId = $_SESSION['id'];
            ?>
            </tbody>
        </table>
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