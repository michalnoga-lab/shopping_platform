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
        <h3>Dostawa na adres</h3>
        <hr>
        <table class="table table-hover">
            <thead>
            <tr>
                <th scope="col" style="width: 10%">#</th>
                <th scope="col" style="width: 70%">Adres</th>
                <th scope="col" style="width: 20%">Telefon</th>
            </tr>
            </thead>
            <tbody>
            <?php
            include_once '../classes/dbh.classes.php';

            $userId = $_SESSION['id'];
            $stmt = $connection->prepare('SELECT * FROM addresses WHERE user_id = ?;');
            $stmt->bind_param('i', $userId);
            $stmt->execute();
            $result = $stmt->get_result();
            $addresses = $result->fetch_all(MYSQLI_ASSOC);

            if (sizeof($addresses) > 0) {
                $rowNumber = 0;
                foreach ($addresses as $address) {
                    $rowNumber += 1; ?>
                    <tr onclick="window.location='address-single.php?id='+<?= $address['id'] ?>">
                        <!-- TODO formularz i zapisanie ID do bazy -->
                        <td><?= $rowNumber ?></td>
                        <td><?= $address['street'] ?></td>
                        <td><?= $address['phone'] ?></td>
                    </tr>
                <?php }
            } else {
                echo('<div class="alert alert-danger text-center" role="alert">Brak adresów do wyświetlenia. Dodaj adres dostawy.</div>');
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