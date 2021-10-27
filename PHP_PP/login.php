<?php
// error_reporting(0); TODO włączyć na wszystkich stronach

?>

<!doctype html>
<html lang="pl">
<head>
    <?php include_once('includes/head.inc.php') ?>
    <title>Prima Platforma | Login</title>
    <link href="static/css/login.css" rel="stylesheet"/>
</head>
<body>

<div class="container">
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <div class="panel panel-login">
                <div class="panel-heading">
                    <div class="row">
                        <div class="col-xs-6">
                            <a href="#" class="active">Logowanie</a>
                        </div>
                        <div class="col-xs-6">
                            <a href="register.php">Rejestracja</a>
                        </div>
                    </div>
                    <hr>
                </div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <form id="login-form" action="#" method="post" role="form" style="display: block;">
                                <div class="form-group">
                                    <label for="email"></label>
                                    <input type="email" name="email" id="email" class="form-control"
                                           placeholder="Podaj email" value="">
                                </div>
                                <div class="form-group">
                                    <label for="password"></label>
                                    <input type="password" name="password" id="password"
                                           class="form-control" placeholder="Podaj hasło" value="">
                                </div>
                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-sm-6 col-sm-offset-3">
                                            <input type="submit" name="login-submit" id="login-submit" tabindex="4"
                                                   class="form-control btn btn-login" value="Logowanie">
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-lg-12">
                                            <!-- <div class="text-center">
                                                <a href="#" tabindex="5" class="forgot-password">Przypomnienie hasła</a>
                                            </div> -->
                                            <!-- TODO funkcja przypominania hasła -->
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

<script type="text/javascript" src="static/js/login.js"></script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>
</body>
</html>