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

        if ($infoCheck == 'cart_saved') {
            echo('<div class="alert alert-success text-center" role="alert">Produkt dodany do koszyka</div>');
        } elseif ($infoCheck == 'connection') {
            echo('<div class="alert alert-danger text-center" role="alert">Błąd połączenia</div>');
        }
    }
    ?>
    <div class="row">
        <h3>Moje produkty</h3>
        <hr>
        <table class="table table-hover">
            <thead>
            <tr>
                <th scope="col" style="width: 10%">#</th>
                <th scope="col" style="width 70%">Nazwa</th>
                <th scope="col" style="width: 20%">Cena</th>
            </tr>
            </thead>
            <tbody>
            <?php
            include_once '../classes/dbh.classes.php';

            $stmt = $connection->prepare('SELECT * FROM products;');
            $stmt->execute();
            $result = $stmt->get_result();
            $products = $result->fetch_all(MYSQLI_ASSOC);

            if ($products > 0) {
                $rowNumber = 0;
                foreach ($products as $product) {
                    $rowNumber += 1; ?>
                    <tr onclick="window.location='product-single-add.php?id='+<?= $product['id'] ?>">
                        <td><?= $rowNumber ?></td>
                        <td><?= $product['name'] ?></td>
                        <td><?= $product['nett_price'] ?> PLN</td>
                        <!-- TODO w zależności od ustawień wyświetlamy cenę netto lub brutto -->
                    </tr>
                <?php }
            } else {
                echo('<div class="alert alert-danger text-center" role="alert">Brak produktów do wyświetlenia</div>');
            }
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