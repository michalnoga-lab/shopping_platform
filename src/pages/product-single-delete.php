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
        <h3>Usuwanie produktu</h3>
        <hr>
        <?php
        if (isset($_GET['id'])) {
            $productId = $_GET['id'];
        } else {
            $productId = -1;
        }
        ?>
        <form action="cart.php">
            <button type="submit" class="btn btn-block btn-success mb-3">Nie usuwaj</button>
        </form>
        <form action="/src/includes/product-single-delete.inc.php" method="post" id="form">
            <input type="hidden" value="<?= $productId ?>" id="product-id" name="product-id"/>
            <button type="submit" id="submit" name="submit" class="btn btn-block btn-danger">Usuń produkt
            </button>
        </form>

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