<?php
// error_reporting(0); TODO
include_once '../includes/roles.inc.php';

class Register extends Dbh
{
    protected function isEmailNotInDb($email)
    {
        $stmt = $this->connect()->prepare('SELECT email FROM users WHERE email = ?;');

        if (!$stmt->execute(array($email))) {
            $stmt = null;
            header("location: ../index.php?error=connection"); // TODO logs
            exit();
        }

        if ($stmt->rowCount() > 0) {
            return false;
        } else {
            return true;
        }
    }

    public function saveUser($username, $email, $password)
    {
        if ($this->isEmailNotInDb($email)) {

            $stmt = $this->connect()->prepare('INSERT INTO users (username, email, password, role) VALUES (?, ?, ?, ?);');
            $hashed_password = password_hash($password, PASSWORD_DEFAULT);

            if ($stmt->execute(array($username, $email, $hashed_password, 'USER'))) { // TODO enum zamiast na sztywno USER
                $stmt = null;
                header('location: ../index.php?error=user_added'); // TODO zapisanie do logów
                exit();
            }
        } else {
            header('location: ../pages/register.php?error=email_in_db'); // TODO zapisanie do logów
            exit();
        }

        $stmt = null;
    }
}