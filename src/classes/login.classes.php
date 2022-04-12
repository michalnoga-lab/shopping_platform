<?php
//error_reporting(0); // TODO
require_once '../includes/logger.inc.php';

class Login extends Dbh
{
    private string $location = 'location: ../pages/login.php?info=';

    protected function loginUser($email, $password)
    {
        $logger = new Logger();

        $stmt = $this->connect()->prepare('SELECT * FROM users WHERE email = ?;');

        if (!$stmt->execute(array($email))) {
            $stmt = null;
            header($this->location . 'stmt_failed');
            exit();
        }

        if ($stmt->rowCount() == 0) {
            $stmt = null;
            $logger->systemEvent('Failed login attempt with nonexistent email: ' . $email);
            header($this->location . 'user_not_found');
            exit();
        }

        $user = $stmt->fetch();

        if ($user[6] == false) {
            $stmt = null;
            header($this->location . 'account_disabled');
            exit();
        }

        $check_passwords = password_verify($password, $user[3]);

        if ($check_passwords == false) {
            $stmt = null;
            $logger->systemEvent('Failed login attempt with wrong password for email ' . $email);
            header($this->location . 'invalid_password');
            exit();
        }

        session_start();
        $_SESSION['id'] = $user[0];
        $_SESSION['username'] = $user[1];
        $_SESSION['email'] = $user[2];
        $_SESSION['role'] = $user[4];

        $logger->systemEvent('Login successful');
        $stmt = null;
    }
}