<?php
// error_reporting(0) TODO

function displayLoginResponseAlert($response)
{

    echo '<script type="text/javascript">alert("1");</script>';

    if (isset($_GET['error'])) {
        if ($response === 'login_empty_input') {
            echo '
            <div class="alert alert-danger" role="alert">
            <script type="text/javascript">alert("2");</script>
            <p>Wszystkie pola muszą być wypełnione!</p>
            </div>
            ';
        } else if ($response === 'login_success') {
            echo '
            <div class="alert alert-success" role="alert">
            <p>Udało się!</p>
            <p>Prawidłowe logowanie</p>
            </div>
            ';
        }
    }
}