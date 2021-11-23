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
}