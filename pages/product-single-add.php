<?php
// error_reporting(0); TODO
session_start();

?>

<!doctype html>
<html lang="pl">
<head>
    <?php include_once('../static/elements/head.inc.php'); ?>
    <link href="/static/css/validation.css" rel="stylesheet"/>
    <title>Prima Platforma</title>
</head>

<body>
<script defer src="../static/js/product/add-validation.js"></script>
<header>
    <?php include_once('../static/elements/header.php') ?>
</header>

<div class="container">
    <?php
    if (isset($_GET['id'])) {
        include_once '../classes/dbh.classes.php';
        $id = $_GET['id'];

        $stmt = $connection->prepare('SELECT * FROM products WHERE id = ?;');
        $stmt->bind_param('i', $id);
        $stmt->execute();
        $result = $stmt->get_result();

        if ($result > 0) {
            $product = $result->fetch_assoc(); ?>
            <form action="/includes/product-single-add.inc.php" method="post" id="form">
                <h3><?= $product['name'] ?></h3>
                <hr>
                <p>Numer pozycji w przetargu: <?= $product['auction_number'] ?></p>
                <p>Cena netto: <?= $product['nett_price'] ?> PLN</p>
                <p>VAT: <?= $product['vat'] ?> %</p>
                <p>Cena brutto: <?= $product['gross_price'] ?> PLN</p>
                <hr>
                <div class="form-floating mb-3 input-control">
                    <input type="number" class="form-control" id="quantity" name="quantity" placeholder="Podaj ilość">
                    <label for="quantity">Podaj ilość</label>
                    <input type="hidden" value="<?= $id ?>" id="product-id" name="product-id"/>
                    <input type="hidden" value="<?= $product['nett_price'] ?>" id="nett-price" name="nett-price"/>
                    <input type="hidden" value="<?= $product['vat'] ?>" id="vat" name="vat"/>
                    <input type="hidden" value="<?= $product['gross_price'] ?>" id="gross-price" name="gross-price"/>
                    <input type="hidden" value="" id="comment" name="comment"/>
                    <!-- TODO aktywować pole komentarza tak, żeby można było dodawać np rozmiar do produktu -->
                    <div class="error"></div>
                </div>
                <button type="submit" id="submit" name="submit" class="btn btn-primary btn-block">Dodaj do koszyka
                </button>
            </form>
        <?php } else {
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