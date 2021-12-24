<?php
// error_reporting(0); TODO
session_start();

?>

<!doctype html>
<html lang="pl">
<head>
    <?php include_once('../static/elements/head.inc.php'); ?>
    <link href="/static/css/validation.css" rel="stylesheet">
    <title>Prima Platforma</title>
</head>

<body>
<script defer src="../static/js/account/password-validation.js"></script>
<header>
    <?php include_once('../static/elements/header.php') ?>
</header>

<div class="container">
    <h3>Zmiana hasła</h3>
    <hr>
    <form action="../includes/account-password.inc.php" method="post" id="form">
        <div class="form-floating mb-3 input-control">
            <input type="password" class="form-control" id="password" name="password" placeholder="Podaj nowe hasło">
            <label for="password">Podaj nowe hasło</label>
            <div class="error"></div>
        </div>
        <div class="form-floating mb-3 input-control">
            <input type="password" class="form-control" id="passwordConfirmation" name="passwordConfirmation"
                   placeholder="Powtórz nowe hasło">
            <label for="passwordConfirmation">Powtórz nowe hasło</label>
            <div class="error"></div>
        </div>
        <button class="btn btn-primary btn-block" type="submit" id="submit" name="submit">Zmień hasło</button>
    </form>
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