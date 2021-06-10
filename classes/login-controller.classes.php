<?php
// error_reporting(0); // TODO

class LoginController extends Login
{

    private string $email;
    private string $password;

    public function __construct($email, $password)
    {
        $this->email = $email;
        $this->password = $password;
    }

    public function doLogin()
    {
        if ($this->areAllFieldsFilled() == false) {
            header('location: ../pages/login.php?info=empty_input');
            exit();
        }
        $this->loginUser($this->email, $this->password);
    }

    private function areAllFieldsFilled(): bool
    {
        if (empty($this->email) || empty($this->password)) {
            return false;
        }
        return true;
    }
}