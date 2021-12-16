<?php
// error_reporting(0); TODO

class RegisterController extends Register
{
    private string $username;
    private string $email;
    private string $password;
    private string $passwordConfirmation;
    private string $location = 'location: ../pages/register.php?error=';

    public function __construct($username, $email, $password, $passwordConfirmation)
    {
        $this->username = $username;
        $this->email = $email;
        $this->password = $password;
        $this->passwordConfirmation = $passwordConfirmation;
    }

    public function registerUser(): void
    {
        if ($this->areAllFieldsFilled() == false) {
            header($this->location . 'empty_input');
            exit();
        }

        if ($this->isUsernameValid() == false) {
            header($this->location . 'invalid_username');
            exit();
        }

        if ($this->isEmailValid() == false) {
            header($this->location . 'invalid_email');
            exit();
        }

        if ($this->isPasswordValid() == false) {
            header($this->location . 'invalid_password');
            exit();
        }

        if ($this->isPasswordConfirmationValid() == false) {
            header($this->location . 'invalid_confirmation');
            exit();
        }

        if ($this->arePasswordsEqual() == false) {
            header($this->location . 'different_passwords');
            exit();
        }

        $this->saveUser($this->username, $this->email, $this->password);
    }

    private function areAllFieldsFilled(): bool
    {
        if (empty($this->username) | empty($this->email) || empty($this->password) || empty($this->passwordConfirmation)) {
            return false;
        }
        return true;
    }

    private function isUsernameValid()
    {
        if (!preg_match('/^[a-zA-Z0-9\s_-]{0,200}$/', $this->username)) {
            return false;
        }
        return true;
    }

    private function isEmailValid()
    {
        if (!filter_var($this->email, FILTER_VALIDATE_EMAIL)) {
            return false;
        }
        return true;
    }

    private function isPasswordValid()
    {
        if (strlen($this->password) < 8 | strlen($this->password) > 200) {
            return false;
        }
        return true;
    }

    private function isPasswordConfirmationValid()
    {
        if (strlen($this->passwordConfirmation) < 8 | strlen($this->passwordConfirmation) > 200) {
            return false;
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