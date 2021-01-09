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
            $logger->systemEvent('Delivery address added');
        } else {
            header('location: ../address/addresses.php?error=connection');
            exit();
        }
    }
}