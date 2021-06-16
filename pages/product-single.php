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
        include_once '../classes/dbh.classes.php';
        $id = $_GET['id'];

        $stmt = $connection->prepare('SELECT * FROM products WHERE id = ?;');
        $stmt->bind_param('i', $id);
        $stmt->execute();
        $result = $stmt->get_result();

        if ($result > 0) {
            $product = $result->fetch_assoc(); ?>
            <form>
                <h3><?= $product['name'] ?></h3>
                <hr>
                <p>Numer pozycji w przetargu: <?= $product['auction_number'] ?></p>
                <p>Cena netto: <?= $product['nett_price'] ?> PLN</p>
                <p>VAT: <?= $product['vat'] ?> %</p>
                <p>Cena brutto: <?= $product['gross_price'] ?> PLN</p>
                <hr>
                <!-- TODO JS walidacja wprowadzonej ilości -->
                <div class="form-group">
                    <label for="quantity"></label>
                    <input type="number" class="form-control" id="quantity" placeholder="Podaj ilość">
                </div>
                <button type="submit" class="btn btn-primary btn-block">Dodaj do koszyka</button>
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