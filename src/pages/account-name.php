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
<script defer src="../static/js/account/name-validation.js"></script>
<header>
    <?php include_once('../static/elements/header.php') ?>
</header>

<div class="container">
    <h3>Zmiana nazwy</h3>
    <hr>
    <?php
    include_once '../classes/dbh.classes.php';

    $stmt = $connection->prepare('SELECT * from users WHERE id= ?;');
    $stmt->bind_param('i', $_SESSION['id']);
    $stmt->execute();
    $result = $stmt->get_result();

    if ($result > 0) {
        $user = $result->fetch_assoc(); ?>
        <form action="/includes/account-name.inc.php" method="post" id="form">
            <div class="form-floating mb-3 input-control">
                <input type="text" class="form-control" id="name" name="name" placeholder="Nowa nazwa użytkownika">
                <label for="name">Nowa nazwa użytkownika</label>
                <div class="error"></div>
            </div>
            <button class="btn btn-primary btn-block" type="submit" id="submit" name="submit">Zmień nazwę użytkownika
            </button>
        </form>
        <?php
    } else {
        echo('<div class="alert alert-danger text-center" role="alert">Nie odnaleziono danych użytkownika</div>');
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