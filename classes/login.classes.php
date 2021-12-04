<?php
//error_reporting(0); // TODO

class Login extends Dbh
{
    protected function loginUser($email, $password)
    {
        $stmt = $this->connect()->prepare('SELECT password FROM users WHERE email = ?;');

        if (!$stmt->execute(array($email, $password))) {
            $stmt = null;
            header('location: ../index.php?error=stmt_failed'); // TODO obsługa błedu
            exit();
        }

        if ($stmt->rowCount() == 0) {
            $stmt = null;
            header('location: ../index.php?error=user_not_found'); // TODO obsługa błędu
            exit();
        }

        $password_hashed = $stmt->fetchAll(PDO::FETCH_ASSOC);
        $check_passwords = password_verify($password, $password_hashed[0]['password']);

        if ($check_passwords == false) {
            $stmt = null;
            header('location: ../index.php?error=invalid_password'); // TODO obsłouga błędu
            exit();
        }

        $user = $stmt->fetchAll(PDO::FETCH_ASSOC);

        session_start();
        $_SESSION['id'] = $user[0]['id'];
        $_SESSION['username'] = $user[0]['username'];
        $_SESSION['email'] = $user[0]['email'];
        $_SESSION['role'] = $user[0]['role'];

        $stmt = null;
    }
}