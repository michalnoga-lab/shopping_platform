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
            <?php
            include_once '../classes/dbh.classes.php';
            $stmt_cart = $connection->prepare('SELECT * FROM carts WHERE user_id = ? AND closed=false;');
            $stmt_cart->bind_param('i', $_SESSION['id']);
            $stmt_cart->execute();
            $result = $stmt_cart->get_result();
            $carts = $result->fetch_all(MYSQLI_ASSOC);

            if ($carts[0]['nett_value'] == 0) {
                echo('<div class="alert alert-danger text-center" role="alert">Nie masz jeszcze żadnych produktów w koszyku</div>');
            } else { ?>
            <div>
                <p>Wartość zamówienia netto: <?= $carts[0]['nett_value'] ?> PLN</p>
                <p>Wartość VAT: <?= $carts[0]['vat_value'] ?> PLN</p>
                <p>Wartość zamówienia brutto: <?= $carts[0]['gross_value'] ?> PLN</p>
            </div>
            </thead>
            <tbody>
            <?php
            $cartId = $carts[0]['id'];
            $stmt_products = $connection->prepare('SELECT * FROM products_in_cart INNER JOIN products ON products_in_cart.product_id=products.id WHERE cart_id = ?;');
            $stmt_products->bind_param('i', $cartId);
            $stmt_products->execute();
            $result = $stmt_products->get_result();
            $products = $result->fetch_all(MYSQLI_ASSOC);
            $rowNumber = 0;

            foreach ($products as $product) {
                $rowNumber += 1; ?>
                <tr>
                    <td style="width 10%"><?= $rowNumber ?></td>
                    <td style="width 70%"><?= $product['name'] ?></td>
                    <td style="width 20%"><?= $product['quantity'] ?> sztuk</td>
                </tr>
                <?php
            }
            }
            ?>
            </tbody>
        </table>
        <button class="btn btn-block btn-primary">Złóż zamówienie</button>
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