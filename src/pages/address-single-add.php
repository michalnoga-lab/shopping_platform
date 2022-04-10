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
<script defer src="/src/static/js/address/add-validation.js"></script>

<div class="container">
    <?php
    if (isset($_GET['info'])) {
        $infoCheck = $_GET['info'];

        if ($infoCheck == 'empty_input') {
            echo('<div class="alert alert-danger text-center" role="alert">Nie wszystkie pola są wypełnione</div>');
        } elseif ($infoCheck === 'invalid_street') {
            echo('<div class="alert alert-danger text-center" role="alert">Pole adresu nie zostało prawidłowo wypełnione</div>');
        } elseif ($infoCheck == 'invalid_phone') {
            echo('<div class="alert alert-danger text-center" role="alert">Pole numeru telefonu nie zostało prawidłowo wypełnione</div>');
        }
    }
    ?>
    <form action="/src/includes/address-add.inc.php" method="post" id="form">
        <div class="form-floating mb-3 input-control">
            <input type="text" class="form-control" id="street" name="street" placeholder="Adres dostawy">
            <label for="street">Adres dostawy</label>
            <div class="error"></div>
        </div>
        <div class="form-floating mb-3 input-control">
            <input type="text" class="form-control" id="phone" name="phone" placeholder="Telefon kontaktowy">
            <label for="phone">Telefon kontaktowy</label>
            <div class="error"></div>
        </div>
        <div class="d-grid">
            <button class="btn btn-primary btn-login" type="submit" name="submit" id="submit">
                Dodaj adres
            </button>
        </div>
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