<?php
// error_reporting(0); TODO włączyć na wszystkich stronach

?>

<!doctype html>
<html lang="pl">
<head>
    <?php include_once('includes/head.inc.php') ?>
    <title>Prima Platforma | Login</title>
    <link href="/static/css/login.css" rel="stylesheet"/>
</head>
<body>

<div class="container">
    <div class="row">
        <div class="col-sm-9 col-md-7 col-lg-5 mx-auto">
            <div class="card border-0 shadow rounded-3 my-5">
                <div class="card-body p-4 p-sm-5">
                    <h5 class="card-title text-center mb-5 fw-light fs-5">Sign In</h5>
                    <form>
                        <div class="form-floating mb-3">
                            <input type="email" class="form-control" id="floatingInput" placeholder="name@example.com">
                            <label for="floatingInput">Email address</label>
                        </div>
                        <div class="form-floating mb-3">
                            <input type="password" class="form-control" id="floatingPassword" placeholder="Password">
                            <label for="floatingPassword">Password</label>
                        </div>

                        <div class="form-check mb-3">
                            <input class="form-check-input" type="checkbox" value="" id="rememberPasswordCheck">
                            <label class="form-check-label" for="rememberPasswordCheck">
                                Remember password
                            </label>
                        </div>
                        <div class="d-grid">
                            <button class="btn btn-primary btn-login text-uppercase fw-bold" type="submit">Sign
                                in</button>
                        </div>
                        <hr class="my-4">
                        <div class="d-grid mb-2">
                            <button class="btn btn-google btn-login text-uppercase fw-bold" type="submit">
                                <i class="fab fa-google me-2"></i> Sign in with Google
                            </button>
                        </div>
                        <div class="d-grid">
                            <button class="btn btn-facebook btn-login text-uppercase fw-bold" type="submit">
                                <i class="fab fa-facebook-f me-2"></i> Sign in with Facebook
                            </button>
                        </div>
                    </form>
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