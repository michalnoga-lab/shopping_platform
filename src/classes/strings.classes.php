<?php

class StringGenerator
{
    public function generateActivationKey(): string
    {
        return substr(str_shuffle(md5(time())), 0, 32) . substr(str_shuffle(md5(time())), 0, 32);
    }

    public function generateActivationLink($email, $key): string
    {
        $domain = 'http://localhost/src/pages/';
        // TODO ustawić poprawną domenę
        return $domain . 'register-confirm.php?email=' . $email . '&code=' . $key;
    }
}