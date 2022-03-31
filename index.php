<?php
// error_reporting(0);
// TODO
session_start();

// TODO mail aktywacyjny po rejestracji
// TODO readme.md zmienić na dockera
// TODO wyłączyć we wszystkich plikach raportowanie błędów
// TODO specjalna biblioteka do sanitization danych rejestracji i logowania
// TODO zrobić filtrowanie produktów po nazwach
// TODO backup bazy danych ???
// TODO To prevent leaking your password, here's what your php.ini file should look like in production: do both display_errors = Off and log_errors = On. Then restart Apache or Ngnix
// TODO możliwość kopiowania koszyka

//$stmt = $this->mysqli->prepare("UPDATE datadump SET content=? WHERE id=?"); TODO !!!!!
///* BK: always check whether the prepare() succeeded */
//if ($stmt === false) {
//    trigger_error($this->mysqli->error, E_USER_ERROR);
//    return;
//}


//$stmt = $this->mysqli->prepare("UPDATE datadump SET content=? WHERE id=?"); TODO przykład operacji na bazie danych !!!
///* BK: always check whether the prepare() succeeded */
//if ($stmt === false) {
//    trigger_error($this->mysqli->error, E_USER_ERROR);
//    return;
//}
//$id = 1;
///* Bind our params */
///* BK: variables must be bound in the same order as the params in your SQL.
// * Some people prefer PDO because it supports named parameter. */
//$stmt->bind_param('si', $content, $id);
//
///* Set our params */
///* BK: No need to use escaping when using parameters, in fact, you must not,
// * because you'll get literal '\' characters in your content. */
//$content = $_POST['content'] ?: '';
//
///* Execute the prepared Statement */
//$status = $stmt->execute();
///* BK: always check whether the execute() succeeded */
//if ($status === false) {
//    trigger_error($stmt->error, E_USER_ERROR);
//}
//printf("%d Row inserted.\n", $stmt->affected_rows);

?>

<!doctype html>
<html lang="pl">
<head>
    <?php include_once('static/elements/head.inc.php'); ?>
    <link href="/static/css/index.css" rel="stylesheet"/>
    <title>Prima Platforma</title>
</head>
<body>

<header>
    <?php include_once('static/elements/header.php') ?>
</header>

<div class="col-12">
    <?php
    if (isset($_GET['info'])) {
        $errorCheck = $_GET['info'];

        if ($errorCheck === 'user_added') {
            echo('<div class="alert alert-success text-center" role="alert">Użytkownik poprawnie dodany do bazy danych</div>');
        } elseif ($errorCheck === 'login_successful') {
            echo('<div class="alert alert-success text-center" role="alert">Poprawne logowanie</div>');
        } elseif ($errorCheck === 'connection') {
            echo('<div class="alert alert-danger text-center" role="alert">Błąd połączenia</div>');
        } elseif ($errorCheck === 'logged_out') {
            echo('<div class="alert alert-success text-center" role="alert">Koniec pracy. Poprawne wylogowano!</div>');
        }
    }
    ?>
    <div class="h-100 p-5 bg-light border rounded-3">
        <h2>O platformie</h2>
        <p>Platforma jest dostępna tylko dla klientów przetargowych, którzy mają podpisaną z nami umowę.</p>
        <p>Coś nie działa, masz pomysł, sugestię co zmienić na platformie, potrzebujesz nowej
            funkcjonalności - napisz do nas - biuro@primakrakow.pl</p>
        <p>Kontakt telefoniczny: (12) 411 25 09, (12) 411 67 54</p>
        <p>Sprawdź naszą politykę <a href='/pages/cookies.php'>Cookies</a></p>
        <p>Sprawdź jak przetwarzamy dane <a href='/pages/rodo.php'>RODO</a></p>
    </div>
</div>


<footer>
    <?php include_once('static/elements/footer.php') ?>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous">
</script>
</body>
</html>