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
        <h3>Szczegóły zamówienia</h3>
        <hr>
        <?php
        if (isset($_GET['id'])) {
            include_once '../classes/dbh.classes.php';
            $cartId = $_GET['id'];

            $stmt_cart = $connection->prepare('SELECT * FROM carts INNER JOIN addresses on carts.address_id = addresses.id WHERE carts.id = ?;');
            $stmt_cart->bind_param('i', $cartId);
            $stmt_cart->execute();
            $result = $stmt_cart->get_result();

            if ($result > 0) {
                $cart = $result->fetch_assoc(); ?>
                <div>
                    <p class="col-12">Wartość zamówienia netto: <?= $cart['nett_value'] ?> PLN</p>
                    <p class="col-12">Wartość VAT: <?= $cart['vat_value'] ?> PLN</p>
                    <p class="col-12">Wartość zamówienia brutto: <?= $cart['gross_value'] ?> PLN</p>
                    <p class="col-12">Adres dostawy: <?= $cart['street'] ?></p>
                    <p class="col-12">Zamówienie z dnia: <?= $cart['purchased'] ?></p>
                </div>
                <hr>
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th scope="col" style="width: 10%">#</th>
                        <th scope="col" style="width: 70%">Nazwa produktu</th>
                        <th scope="col" style="width: 20%">Ilość</th>
                    </tr>
                    </thead>
                    <tbody>
                    <?php
                    $stmt_products = $connection->prepare('SELECT * FROM products_in_cart INNER JOIN products on products_in_cart.product_id = products.id WHERE products_in_cart.cart_id = ?;');
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
                            <td style="width 20%"><?= $product['quantity'] ?> SZT</td>
                        </tr>
                        <!-- TODO duplikowanie koszyka / zamów to samo jeszcze raz -->
                    <?php }
                    ?>
                    </tbody>
                </table>
                <?php
            } else {
                echo('<div class="alert alert-danger text-center" role="alert">Błąd wczytywania koszyka</div>');
            }
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