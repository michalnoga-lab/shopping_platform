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
<script defer src="/src/static/js/common/spinner.js"></script>
<header>
    <?php include_once('../static/elements/header.php') ?>
</header>

<div class="container">
    <?php
    $addressId = $_GET['id'] ?? 0;
    ?>
    <form action="/src/includes/cart-submit.inc.php" method="post" id="form" name="form">
        <input type="hidden" value="<?= $addressId ?>" name="address-id" id="address-id"/>
        <button class="btn btn-block btn-success" name="submit" id="submit">Prześlij zamówienie do realizacji</button>
    </form>
</div>
<div class="loader" style="visibility: hidden" id="spinner">
    <div class="spinner-border" role="status">
        <span class="sr-only">Przetwarzanie zamówienia ...</span>
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