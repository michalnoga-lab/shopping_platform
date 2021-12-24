<?php
// TODO errors
// error_reporting(0);
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
<script defer src="../static/js/register/validation.js"></script>
<div class="container">
    <?php
    if (isset($_GET['info'])) {
        $infoCheck = $_GET['info'];

        if ($infoCheck === 'empty_input') {
            echo '<div class="alert alert-danger text-center" role="alert">Nie podano danych do rejestracji</div>';
        } elseif ($infoCheck === 'invalid_username') {
            echo('<div class="alert alert-danger text-center" role="alert">Błędna nazwa użytkownika</div>');
        } elseif ($infoCheck === 'invalid_email') {
            echo('<div class="alert alert-danger text-center" role="alert">Błędny adres email</div>');
        } elseif ($infoCheck === 'invalid_password') {
            echo('<div class="alert alert-danger text-center" role="alert">Błędne hasło</div>');
        } elseif ($infoCheck === 'invalid_confirmation') {
            echo('<div class="alert alert-danger text-center" role="alert">Błędne potwierdzenie hasła</div>');
        } elseif ($infoCheck === 'different_passwords') {
            echo('<div class="alert alert-danger text-center" role="alert">Podane hasła nie są takie same</div>');
        } elseif ($infoCheck === 'email_in_db') {
            echo('<div class="alert alert-danger text-center" role="alert">Taki email jest już w użyciu. Proszę wybrać inny</div>');
        } elseif ($infoCheck === 'connection') {
            echo('<div class="alert alert-danger text-center" role="alert">Błąd połączenia</div>');
        }
    }
    ?>

    <div class="col-sm-9 col-md-7 col-lg-5 mx-auto">
        <div class="card border-0 shadow rounded-3 my-5">
            <div class="card-body p-4 p-sm-5">
                <h5 class="card-title text-center mb-5 fw-light fs-5">Rejestracja</h5>
                <form action="/includes/register.inc.php" method="post" id="form">
                    <div class="form-floating mb-3 input-control">
                        <input type="text" class="form-control" id="username" name="username"
                               placeholder="Imię i nazwisko"
                               required>
                        <label for="username">Imię i Nazwisko</label>
                        <div class="error"></div>
                    </div>
                    <div class="form-floating mb-3 input-control">
                        <input type="email" class="form-control" id="email" name="email" placeholder="Adres email"
                               required>
                        <label for="email">Adres email</label>
                        <div class="error"></div>
                    </div>
                    <div class="form-floating mb-3 input-control">
                        <input type="password" class="form-control" id="password" name="password"
                               placeholder="Hasło" required>
                        <label for="password">Hasło</label>
                        <div class="error"></div>
                    </div>
                    <div class="form-floating mb-3 input-control">
                        <input type="password" class="form-control" id="password-confirmation"
                               name="passwordConfirmation"
                               placeholder="Powtórz hasło" required>
                        <label for="password-confirmation">Powtórz hasło</label>
                        <div class="error"></div>
                    </div>
                    <div class="d-grid">
                        <button class="btn btn-primary btn-login text-uppercase fw-bold" type="submit" id="submit"
                                name="submit">Zarejestruj konto
                        </button>
                    </div>
                    <hr class="my-4">
                </form>
            </div>
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