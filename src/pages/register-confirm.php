<?php
// TODO errors
// error_reporting(0);
include_once '../classes/register.classes.php';
?>

<!doctype html>
<html lang="pl">
<head>
    <?php include_once('../static/elements/head.inc.php'); ?>
    <link href="/src/static/css/validation.css" rel="stylesheet"/>
    <title>Prima Platforma</title>
</head>

<body>
<div class="container">
    <?php
    if (isset($_GET['email']) && isset($_GET['code'])) {
        $email = $_GET['email'];
        $code = $_GET['code'];

        $register = new Register();
        $activationResult = $register->activateUser($email, $code);

        if ($activationResult) {
            echo('<div class="alert alert-success text-center" role="alert">Użytkownik aktywowany</div>');
        } else {
            echo('<div class="alert alert-danger text-center" role="alert">Błąd aktywacji konta użytkownika</div>');
        }
    }
    ?>

    <div class="col-sm-9 col-md-7 col-lg-5 mx-auto">
        <div class="card border-0 shadow rounded-3 my-5">

            <a href="/index.php" class="btn btn-outline-secondary" type="button">Powrót do strony głównej</a>
        </div>
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