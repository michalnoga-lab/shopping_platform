<?php
// error_reporting(0) TODO

function displayResponseAlert($response)
{
    if (isset($_GET['error'])) {
        if ($response === 'empty_input') {
            echo '
            <div class="alert alert-danger" role="alert">
            <p>Wszystkie pola muszą być wypełnione!</p>
            </div>
            ';
        } else if ($response === 'invalid_username') {
            echo '
            <div class="alert alert-danger" role="alert">
            <p>Nieprawidłowa nazwa użytkownika!</p>
            <p>Dozwolone są tylko polskie litery i spacje. Maksymalna długość 200 znaków.</p>
            </div>
            ';
        } else if ($response === 'invalid_email') {
            echo '
            <div class="alert alert-danger" role="alert">
            <p>Nieprawidłowy email!</p>
            <p>Wprowadzony ciąg znaków to musi być email. Maksymalna długość 200 znaków.</p>
            </div>
            ';
        } else if ($response === 'invalid_password') {
            echo '
            <div class="alert alert-danger" role="alert">
            <p>Nieprawidłowe hasło!</p>
            <p>Minimalna długość hasła to 8 znaków, maksymalna to 200 znaków.</p>
            <p>Dozwolone są małe i duże litery, cyfry i znaki specjalne.</p>
            </div>
            ';
        } else if ($response === 'different_passwords') {
            echo '
            <div class="alert alert-danger" role="alert">
            <p>Hasła nie są takie same!</p>
            <p>Wprowadzone hasła w polu hasło i powtórz hasło muszą być takie same.</p>
            </div>
            ';
        } else if ($response === 'email_taken') {
            echo '
            <div class="alert alert-danger" role="alert">
            <p>Zajęty adres!</p>
            <p>Użytkownik o takim adresie email już istnieje. Proszę podać inny adres email.</p>
            </div>
            ';
        } else if ($response === 'none') {
            echo '
            <div class="alert alert-success" role="alert">
            <p>Udało się!</p>
            <p>Użytkownik poprawnie dodany do bazy danych.</p>
            </div>
            ';
        }
    }
}