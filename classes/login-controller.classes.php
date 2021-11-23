<?php
// error_reporting(0); // TODO

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
        if ($this->areAllFieldsFilled() == false) {
            header('location: ../index.php?error=empty_input'); // TODO komunikat walidujący
            exit();
        }
        $this->loginUser($this->email, $this->password);
    }

    private function areAllFieldsFilled()
    {
        if (empty(empty($this->email) || empty($this->password))) {
            return false;
        }
        return true;
    }
}