<?php
//error_reporting(0); // TODO

class Login extends Dbh
{
    protected function loginUser($email, $password)
    {
        $stmt = $this->connect()->prepare('SELECT * FROM users WHERE email = ?;');

        if (!$stmt->execute(array($email))) {
            $stmt = null;
            header('location: ../pages/login.php?error=stmt_failed');
            exit();
        }

        if ($stmt->rowCount() == 0) {
            $stmt = null;
            header('location: ../pages/login.php?error=user_not_found'); // TODO zapis do logów
            exit();
        }

        $user = $stmt->fetch();
        $check_passwords = password_verify($password, $user[3]);

        if ($check_passwords == false) {
            $stmt = null;
            header('location: ../pages/login.php?error=invalid_password'); // TODO zapis do logów
            exit();
        }

        session_start();
        $_SESSION['id'] = $user[0];
        $_SESSION['username'] = $user[1];
        $_SESSION['email'] = $user[2];
        $_SESSION['role'] = $user[4];

        $stmt = null; // TODO zapis do logów, że poprawnie zalogowano
    }
}