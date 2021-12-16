<?php
//error_reporting(0); // TODO

class Login extends Dbh
{
    protected function loginUser($email, $password)
    {
        $stmt = $this->connect()->prepare('SELECT * FROM users WHERE email = ?;');

        if (!$stmt->execute(array($email))) {
            $stmt = null;
            header('location: ../index.php?error=stmt_failed'); // TODO obsługa błędu
            exit();
        }

        if ($stmt->rowCount() == 0) {
            $stmt = null;
            header('location: ../index.php?error=user_not_found'); // TODO obsługa błędu
            exit();
        }

        $user = $stmt->fetch();
        $check_passwords = password_verify($password, $user[3]);

        if ($check_passwords == false) {
            $stmt = null;
            header('location: ../index.php?error=invalid_password'); // TODO obsługa błędu
            exit();
        }

        session_start();
        $_SESSION['id'] = $user[0];
        $_SESSION['username'] = $user[1];
        $_SESSION['email'] = $user[2];
        $_SESSION['role'] = $user[4];

        $stmt = null;
    }
}