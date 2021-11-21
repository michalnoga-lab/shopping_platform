<?php
// error_reporting(0); TODO

class RegisterController
{
    private $email;
    private $password;
    private $passwordConfirmation;

    public function __construct($email, $password, $passwordConfirmation)
    {
        $this->email = $email;
        $this->password = $password;
        $this->passwordConfirmation = $passwordConfirmation;
    }

    private function isInputNotEmpty()
    {
        $result = false;

        // TODO czy z dolarkami ???
        // TODO osobne metody dla każdego pola ???
        if (empty($this->email) || empty($this->password) || empty($this->passwordConfirmation)) {
            $result = false;
        }
        $result = true;

        // TODO do poprawy!
        return result;
    }

    // TODO do poprawy !!!
    private function isEmailValid()
    {
        if (!filter_var($this->email, FILTER_VALIDATE_EMAIL)) {
            $result = false;
        }
        return true;
    }

    // TODO do poprawy !!!
    private function isPasswordValid()
    {
        $result = false;

        if (!preg_match('/^[a-zA-Z0-9@\.]+$/', $this->password)) {
            $result = false;
        }
        return true;
    }

    private function arePasswordsEqual()
    {
        if ($this->password !== $this->passwordConfirmation) {
            return false;
        }
        return true;
    }
}