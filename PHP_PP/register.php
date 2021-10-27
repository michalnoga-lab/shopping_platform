<?php
// error_reporting(0); TODO włączyć na wszystkich stronach

?>

<!doctype html>
<html lang="pl">
<head>
    <?php include_once('includes/head.inc.php') ?>
    <title>Prima Platforma | Rejestracja</title>
    <link href="static/css/registration.css" rel="stylesheet"/>
</head>
<body>

<div class="container">
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <div class="panel panel-login">
                <div class="panel-heading">
                    <div class="row">
                        <div class="col-xs-6">
                            <a href="login.php">Logowanie</a>
                        </div>
                        <div class="col-xs-6">
                            <a href="#" class="active">Rejestracja</a>
                        </div>
                    </div>
                    <hr>
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-lg-12">
                                <form id="register-form" action="includes/register/register.inc.php" method="post"
                                      role="form" style="display: none;">
                                    <div class="form-group">
                                        <label for="username"></label>
                                        <input type="text" name="username" id="username" tabindex="1" class="form-control"
                                               placeholder="Imię" value="" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="email"></label>
                                        <input type="email" name="email" id="email" tabindex="1" class="form-control"
                                               placeholder="Adres email" value="" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="password"></label>
                                        <input type="password" name="password" id="password" tabindex="2"
                                               class="form-control" placeholder="Hasło" required value="">
                                    </div>
                                    <div class="form-group">
                                        <label for="confirm-password"></label>
                                        <input type="password" name="confirm-password" id="confirm-password" tabindex="2"
                                               class="form-control" placeholder="Powtórz hasło" required value="">
                                    </div>
                                    <div class="form-group">
                                        <div class="row">
                                            <div class="col-sm-6 col-sm-offset-3">
                                                <input type="submit" name="register-submit" id="register-submit"
                                                       tabindex="4" class="form-control btn btn-register"
                                                       value="Rejestracja">
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="static/js/login.js"></script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>
</body>
</html>