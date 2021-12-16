<?php


class RegisterControllerExposed extends RegisterController
{
    public function registerUserExposed($username, $email, $password, $passwordConfirmation)
    {
        $this->registerUser();
    }
}

class RegisterControllerExposedTests extends \PHPUnit\Framework\TestSuite
{

}