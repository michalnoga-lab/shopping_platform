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
        <h3>Moje zakupy</h3>
        <hr>
        <table class="table table-hover">
            <?php
            include_once '../classes/dbh.classes.php';
            $userId = $_SESSION['id'];
            $stmt = $connection->prepare('SELECT * FROM carts WHERE user_id = ? AND closed = true;');
            $stmt->bind_param('i', $userId);
            $stmt->execute();
            $result = $stmt->get_result();
            $carts = $result->fetch_all(MYSQLI_ASSOC);

            if (count($carts) == 0) {
                echo('<div class="alert alert-danger text-center" role="alert">Nie masz jeszcze żadnych zrealizowanych zamówień</div>');
            } elseif ($carts > 0) {
                $rowNumber = 0;
                foreach ($carts as $cart) {
                    $rowNumber += 1; ?>
                    <tr onclick="window.location='history-single.php?id='+<?= $cart['id'] ?>">
                        <td><?= $rowNumber ?> - zamówienie z dnia: <?= explode(' ', $cart['purchased'])[0] ?>
                            godzina <?= substr_replace(explode(' ', $cart['purchased'])[1], "", -3) ?></td>
                        <td>Wartość netto: <?= $cart['nett_value'] ?> PLN</td>
                        <td>Wartość VAT: <?= $cart['vat_value'] ?> PLN</td>
                        <td>Wartość brutto: <?= $cart['gross_value'] ?> PLN</td>
                    </tr>
                    <?php
                }
            } else {
                echo('<div class="alert alert-danger text-center" role="alert">Nie masz jeszcze żadnych zrealizowanych zamówień</div>');
            }
            ?>
        </table>
        <!-- TODO skopiowanie koszyka do zamówienia -->
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