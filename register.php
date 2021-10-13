<?php
// error_reporting(0);

?>

<!DOCTYPE html>
<html lang="pl">
<head>
    <title>Platforma zakupowa | Admin | Rejestracja nowego użytkownika</title>
    <?php include_once('html_head.inc.php'); ?>
</head>

<body>
<div id="contact">
    <div class="container">
        <div class="row">
            <h2 class="centered">Rejestracja</h2>
            <hr>
        </div>
        <div class="row centered">
            <div class="col-10">
                <form id="login-form" method="post" class="form" role="form" action="includes/register.inc.php">
                    <div class="row">
                        <div class="col-md-12 form-group">
                            <label for="first_name"></label>
                            <input class="form-control input" id="first_name" name="first_name"
                                   placeholder="Podaj imię" type="text" required maxlength="200"/>
                        </div>
                        <div class="col-md-12 form-group">
                            <label for="last_name"></label>
                            <input class="form-control input" id="last_name" name="last_name" placeholder="Nazwisko"
                                   type="text" required maxlength="200"/>
                        </div>
                        <div class="col-md-12 form-group">
                            <label for="email"></label>
                            <input class="form-control input" id="email" name="email" placeholder="Podaj email"
                                   type="text" required maxlength="200"/> <!-- TODO regex -> pattern-->
                        </div>
                        <div class="col-md-12 form-group">
                            <label for="password"></label>
                            <input class="form-control" id="password" name="password" placeholder="Podaj hasło"
                                   type="password" required maxlength="200"/> <!-- TODO regex -->
                        </div>
                        <div class="col-md-12 form-group">
                            <label for="password_confirmation"></label>
                            <input class="form-control" id="password_confirmation" name="password_confirmation"
                                   placeholder="Powtórz hasło" type="password" required maxlength="200"/>
                        </div>
                        <div class="col-md-12 form-group">
                            <button class="btn btn-lg" type="submit" name="submit">Dodaj</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

</body>
</html>