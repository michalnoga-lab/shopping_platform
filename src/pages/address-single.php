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

        $stmt = $connection->prepare('SELECT * FROM addresses WHERE id = ?;');
        $stmt->bind_param('i', $id);
        $stmt->execute();
        $result = $stmt->get_result();

        if ($result > 0) {
            $address = $result->fetch_assoc(); ?>
            <form action="/src/includes/address-remove.inc.php" method="post" id="form">
                <h3>Szczegóły adresu</h3>
                <hr>
                <div class="form-floating mb-3 input-control">
                    <input type="text" class="form-control" id="address" name="address" required hidden
                           value="<?= $id ?>">
                    <label for="address"></label>
                </div>
                <p><?= $address['street'] ?></p>
                <p><?= $address['phone'] ?></p>
                <hr>
                <button class="btn btn-primary btn-block" type="submit" id="submit" name="submit">Usuń adres
                </button>
            </form>
            <?php
        } else {
            echo('<div class="alert alert-danger text-center" role="alert">Nie odnaleziono adresu</div>');
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