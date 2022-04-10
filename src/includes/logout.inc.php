<?php
// error_reporting(0); // TODO

include_once './logger.inc.php';

session_start();

$logger = new Logger();
$logger->systemEvent('Logout successful');

session_unset();
session_destroy();

header('location: /index.php?info=logged_out');