<?php

$server = "localhost";
$username = "root";
$password = "";
$dbname = "platform";

$connection = mysqli_connect($server, $username, $password, $dbname);

if (!$connection) {
    die('Błąd połączenia z bazą danych ...');
}