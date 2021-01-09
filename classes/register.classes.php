<?php
// error_reporting(0); TODO
include_once '../includes/roles.inc.php';
include_once '../includes/logger.inc.php';

class Register extends Dbh
{
    protected function isEmailNotInDb($email)
    {
        $stmt = $this->connect()->prepare('SELECT email FROM users WHERE email = ?;');

        if (!$stmt->execute(array($email))) {
            $stmt = null;
            header("location: ../index.php?error=connection");
            exit();
        }

        if ($stmt->rowCount() > 0) {
            return false;
        } else {
            return true;
        }
    }

    protected function saveUser($username, $email, $password)
    {
        $logger = new Logger();

        if ($this->isEmailNotInDb($email)) {

            $stmt = $this->connect()->prepare('INSERT INTO users (username, email, password, role) VALUES (?, ?, ?, ?);');
            $hashed_password = password_hash($password, PASSWORD_DEFAULT);

            if ($stmt->execute(array($username, $email, $hashed_password, 'USER'))) { // TODO enum zamiast na sztywno USER
                $stmt = null;
                $logger->systemEvent('Registration successful with email ' . $email . ' as ' . 'USER');
                header('location: ../index.php?error=user_added');
                exit();
            }
        } else {
            $logger->systemEvent('Failed registration attempt with existing email ' . $email);
            header('location: ../address/register.php?error=email_in_db');
            exit();
        }
        $stmt = null;
    }
}