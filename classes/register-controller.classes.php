<?php
// error_reporting(0); TODO

class RegisterController extends Register
{
    private $username;
    private $email;
    private $password;
    private $passwordConfirmation;
    private $location = 'location: ../index.php?error=';

    public function __construct($username, $email, $password, $passwordConfirmation)
    {
        $this->username = $username;
        $this->email = $email;
        $this->password = $password;
        $this->passwordConfirmation = $passwordConfirmation;
    }

    public function registerUser()
    {
        if ($this->isInputEmpty() == false) {
            header($this->location . 'empty_input'); // TODO obsłużyć ten komunikat na stronie głównej
            exit();
        }

        if ($this->isEmailValid() == false) {
            header($this->location . 'invalid_email'); // TODO obsłużyć ten komunikat na stronie głównej
            exit();
        }

        if ($this->isPasswordValid() == false) {
            header($this->location . 'invalid_password'); // TODO obsłużyć ten komunikat na stronie głównej
            exit();
        }

        if ($this->arePasswordsEqual() == false) {
            header($this->location . 'different_passwords'); // TODO obsłużyć ten komunikat na stronie głównej
            exit();
        }

        $this->saveUser($this->username, $this->email, $this->password);
    }

    // TODO czy robię inne metody walidujące ???

    private function isInputEmpty()
    {
        $result = false;

        // TODO czy z dolarkami ???
        // TODO osobne metody dla każdego pola ???
        if (empty($this->email) || empty($this->password) || empty($this->passwordConfirmation)) {
            $result = false;
        }
        $result = true;

        // TODO do poprawy bo jest zmiana nazwy metody!
        return $result;
    }

    private function isUsernameValid()
    {
        // TODO całe
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

        // TODO może ważna jest tylko długość, a nie jakie znaki ???
        if (!preg_match('/^[a-zA-Z0-9@\\.]+$/', $this->password)) {
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