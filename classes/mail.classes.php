<?php
// error_reporting(0);
include_once 'converter.classes.php';

class MailFunction
{
    public function sendEmail(string $to, string $subject, string $message, string $headers, string $parameters): void
    {
//        try {
//            mail($to, $subject, $message, $headers, $parameters);
//        } catch (Exception $e) {
//            header('location: ../pages/cart-submit-done.php?info=mail_error');
//            exit();
//            // TODO to logs
//        }

        $converter = new Converter();
        $converter->convertToOptimaFormat();
    }

    // TODO parse to optima
}