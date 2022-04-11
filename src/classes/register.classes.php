<?php
// error_reporting(0); TODO
include_once '../includes/logger.inc.php';
include_once 'mail.classes.php';
include_once '../classes/strings.classes.php';
include_once '../classes/dbh.classes.php';

class Register extends Dbh
{
    protected function isEmailNotInDb($email)
    {
        $stmt = $this->connect()->prepare('SELECT email FROM users WHERE email = ?;');

        if (!$stmt->execute(array($email))) {
            $stmt = null;
            header("location: /index.php?info=connection");
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

            $stmt = $this->connect()->prepare('INSERT INTO users (username, email, password, role, activation_key) VALUES (?, ?, ?, ?, ?);');
            $hashed_password = password_hash($password, PASSWORD_DEFAULT);

            $stringGenerator = new StringGenerator();
            $activationKey = $stringGenerator->generateActivationKey();

            if ($stmt->execute(array($username, $email, $hashed_password, 'USER', $activationKey))) { // TODO enum zamiast na sztywno USER
                $stmt = null;
                $mail = new MailBot();
                $mail->sendEmail($email, $stringGenerator->generateActivationLink($email, $activationKey));
                header('location: /index.php?info=user_added');
                exit();
            }
        } else {
            $logger->systemEvent('Failed registration attempt with existing email ' . $email);
            header('location: /src/pages/register.php?info=email_in_db');
            exit();
        }
        $stmt = null;
    }

    public function activateUser($email, $code): bool
    {
        $logger = new Logger();

        $stmt = $this->connect()->prepare('UPDATE users SET enabled = 1 WHERE email = ? AND activation_key = ?;');
        if (!$stmt->execute(array($email, $code))) {
            $stmt = null;
            header('location: /index.php?info=connection');
            exit();
        }

        if ($stmt->rowCount() > 0) {
            $logger->systemEvent('user with email ' . $email . ' activated');
            return True;
        } else {
            $logger->systemEvent('user with email ' . $email . ' activation failed - wrong code: ' . $code);
            return False;
        }
    }
}