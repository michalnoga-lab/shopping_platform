<?php
//error_reporting(0); // TODO
require_once '../includes/logger.inc.php';

class Login extends Dbh
{
    protected function loginUser($email, $password)
    {
        $logger = new Logger();

        $stmt = $this->connect()->prepare('SELECT * FROM users WHERE email = ?;');

        if (!$stmt->execute(array($email))) {
            $stmt = null;
            header('location: ../pages/login.php?error=stmt_failed');
            exit();
        }

        if ($stmt->rowCount() == 0) {
            $stmt = null;
            $logger->systemEvent('Failed login attempt with nonexistent email: ' . $email);
            header('location: ../pages/login.php?error=user_not_found');
            exit();
        }

        $user = $stmt->fetch();
        $check_passwords = password_verify($password, $user[3]);

        if ($check_passwords == false) {
            $stmt = null;
            $logger->systemEvent('Failed login attempt with wrong password for email ' . $email);
            header('location: ../pages/login.php?error=invalid_password');
            exit();
        }

        session_start();
        $_SESSION['id'] = $user[0];
        $_SESSION['username'] = $user[1];
        $_SESSION['email'] = $user[2];
        $_SESSION['role'] = $user[4];

        (new Logger())->systemEvent('Login successful with email ' . $email); // TODO dane przeglądarki i IP

        $stmt = null;
    }
}