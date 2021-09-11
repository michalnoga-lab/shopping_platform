<?php
// error_reporting(0); // TODO
session_start();

include_once '../includes/logger.inc.php';

class AddAddress extends Dbh
{
    protected function saveAddress($street, $phone)
    {
        $logger = new Logger();

        $stmt = $this->connect()->prepare('INSERT INTO addresses (user_id, street, phone) VALUES (?, ?, ?);');
        if ($stmt->execute(array($_SESSION['id'], $street, $phone))) {
            $stmt = null;
            $logger->systemEvent('Address added by user with email ' . $_SESSION['email']);
        } else {
            header('location: ../pages/addresses.php?error=connection');
            exit();
        }
    }
}