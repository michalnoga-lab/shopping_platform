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
    if (isset($_GET['id'])) {
        include_once('../classes/dbh.classes.php');

        $stmt = $connection->prepare('SELECT * FROM products_in_cart WHERE cart_id = ? AND product_id = ?;');
        $stmt->bind_param('ii', $_SESSION['cart-id'], $_GET['id']);
        $stmt->execute();
        $result = $stmt->get_result();

        if ($result > 0) {
            $product = $result->fetch_assoc(); ?>
            <form action="/includes/product-single-edit.inc.php" method="post" id="form">
                <h3><?= $product['name'] ?></h3>
                <hr>
                <p>Numer pozycji w przetargu <?= $product['auction_number'] ?></p>
                <p>Cena netto: <?= $product['nett_price'] ?> PLN</p>
                <p>VAT: <?= $product['vat'] ?> %</p>
                <p>Cena brutto: <?= $product['gross_price'] ?> PLN</p>
                <hr>
                <div class="form-floating mb-3 input-control">
                    <input type="number" class="form-control" id="quantity" name="quantity"
                           placeholder="<?= $product['quantity'] ?>"
                           value="<?= $product['quantity'] ?>"/>
                    <input type="hidden" value="<?= $product['id'] ?>" id="product-id" name="product-id"/>
                    <input type="hidden" value="<?= $product['nett-price'] ?>" id="nett-price" name="nett-price"/>
                    <input type="hidden" value="<?= $product['vat'] ?>" id="vat" name="vat"/>
                    <input type="hidden" value="<?= $product['gross-price'] ?>" id="gross-price" name="gross-price"/>
                    <label for="quantity">Podaj ilość</label>
                </div>
                <button type="submit" id="submit" name="submit" class="btn btn-primary btn-block mb-3">Zmień ilość
                </button>
            </form>
            <button onclick="window.location='product-single-delete.php?id='+<?= $product['id'] ?>"
                    class="btn btn-danger btn-block">Usuń produkt z koszyka
            </button>
            <?php

        } else {
            echo('<div class="alert alert-danger text-center" role="alert">Nie odnaleziono produktu</div>');
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