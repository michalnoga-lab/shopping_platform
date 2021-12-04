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

        //$password_hashed = $stmt->fetchAll(PDO::FETCH_ASSOC);
        $user = $stmt->fetch();

        // TODO del
        $stmt2 = $this->connect()->prepare('INSERT INTO users (username, email, password, role) VALUES (?, ?, ?, 0);');
        $stmt2->execute(array('qqqqqq', $user[0], 'after password check'));

        $check_passwords = password_verify($password, $user[3]);

        if ($check_passwords == false) {
            $stmt = null;
            header('location: ../index.php?error=invalid_password'); // TODO obsłouga błędu
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