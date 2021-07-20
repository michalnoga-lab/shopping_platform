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
        <?php
        if (isset($_GET['info'])) {
            $infoCheck = $_GET['info'];

            if ($infoCheck === 'address_added') {
                echo('<div class="alert alert-success text-center" role="alert">Adres poprawnie dodany</div>');
            } elseif ($infoCheck === 'removed') {
                echo('<div class="alert alert-success text-center" role="alert">Adres poprawnie usunięty</div>');
            } elseif ($infoCheck === 'connection') {
                echo('<div class="alert alert-danger text-center" role="alert">Błąd połączenia</div>');
            }
        }
        ?>
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

            $user_id = $_SESSION['id'];
            $sql = "SELECT * FROM addresses WHERE user_id = '$user_id'";
            $result = mysqli_query($connection, $sql);
            $resultCheck = mysqli_num_rows($result);

            if ($resultCheck > 0) {
                $rowNumber = 0;
                while ($row = mysqli_fetch_assoc($result)) {
                    $rowNumber += 1; ?>
                    <tr onclick="window.location='address-single.php?id='+<?= $row['id'] ?>">
                        <td><?= $rowNumber ?></td>
                        <td><?= $row['street'] ?></td>
                        <td><?= $row['phone'] ?></td>
                    </tr>
                <?php }
            } else {
                echo('<div class="alert alert-danger text-center" role="alert">Brak adresów do wyświetlenia</div>');
            }
            ?>
            </tbody>
        </table>
        <a href="address-single-add.php" class="btn btn-primary btn-block" role="button">Dodaj adres</a>
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