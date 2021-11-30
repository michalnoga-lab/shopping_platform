<?php
// error_reporting(0); TODO

class Register extends Dbh
{
    protected function isEmailNotInDb($email)
    {
        $stmt = $this->connect()->prepare('SELECT email FROM users WHERE email = ?;');

        if (!$stmt->execute(array($email))) {
            $stmt = null;
            header("location: ../index.php?error=stmt_failed"); // TODO przekazywanie błędu
            exit();
        }

        $result = null; // TODO do poprawy
        if ($stmt->rowCount() > 0) {
            $result = false;
        } else {
            $result = true;
        }
        return $result;
    }

    protected function saveUser($username, $email, $password)
    {
        $stmt = $this->connect()->prepare('INSERT INTO users (username, email, password, role) VALUES (?, ?, ?, 0);');
        $hashed_password = password_hash($password, PASSWORD_DEFAULT);

        if ($stmt->execute(array($username, $email, $hashed_password))) {
            $stmt = null;
            header('location: ../index.php?error=stmt_failed'); // TODO czy kod błedu poprawny i obsłużony ???
            exit();
        }

        $stmt = null;
    }
}