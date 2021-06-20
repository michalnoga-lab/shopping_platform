<?php
// error_reporting(0); // TODO

include_once './logger.inc.php';

session_start();

$logger = new Logger();
$logger->systemEvent('Logout successful with email ' . $_SESSION['email']);

session_unset();
session_destroy();

header('location: ../index.php?error=logged_out');