<?php
// error_reporting(0); // TODO
// TODO walidacja czy pola nie są puste i mają jakiś tekst

class LoginController extends Login
{

    private $email;
    private $password;

    public function __construct($email, $password)
    {
        $this->email = $email;
        $this->password = $password;
    }

    public function doLogin()
    {
        if ($this->emptyInput() == false) { // TODO do poprawy
            header('location: ../index.php?error=empty_input');
            exit();
        }
        $this->loginUser($this->email, $this->password);
    }

    private function emptyInput()
    {
        return true;
    }
}