<?php
// error_reporting(0); // TODO

class AddAddressController extends AddAddress
{
    private string $street;
    private string $phone;
    private string $location = 'location: ../pages/address-single-add.php?error=';

    public function __construct($street, $phone)
    {
        $this->street = $street;
        $this->phone = $phone;
    }

    public function addAddress(): void
    {
        if ($this->areAllFieldsFilled() == false) {
            header($this->location . 'empty_input');
            exit();
        }

        if ($this->isStreetValid() == false) {
            header($this->location . 'invalid_street');
            exit();
        }

        if ($this->isPhoneValid() == false) {
            header($this->location . 'invalid_phone');
            exit();
        }

        $this->saveAddress($this->street, $this->phone);
    }

    private function areAllFieldsFilled(): bool
    {
        if (empty($this->street) || empty($this->phone)) {
            return false;
        }
        return true;
    }

    private function isStreetValid(): bool
    {
        if (!preg_match('/^[a-zA-Z0-9\s_-]{0,200}$/', $this->street)) {
            return false;
        }
        return true;
    }

    private function isPhoneValid(): bool
    {
        if (!preg_match('/^[a-zA-Z0-9\s_-]{0,200}$/', $this->phone)) {
            return false;
        }
        return true;
    }
}