<?php
// error_reporting(0); TODO
include_once '../includes/logger.inc.php';
include_once 'mail.classes.php';

class Register extends Dbh
{
    protected function isEmailNotInDb($email)
    {
        $stmt = $this->connect()->prepare('SELECT email FROM users WHERE email = ?;');

        if (!$stmt->execute(array($email))) {
            $stmt = null;
            header("location: ../index.php?info=connection");
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
                $mail = new MailBot();
                $mail->sendEmail($email);
                header('location: ../index.php?info=user_added');
                exit();
            }
        } else {
            $logger->systemEvent('Failed registration attempt with existing email ' . $email);
            header('location: ../pages/register.php?info=email_in_db');
            exit();
        }
        $stmt = null;
    }
}