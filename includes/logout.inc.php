<?php
// error_reporting(0); // TODO

session_start();
session_unset();
session_destroy();

header('location: ../index.php?error=logged_out'); // TODO zapis do logów