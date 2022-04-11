<?php
// error_reporting(0); // TODO

use Symfony\Component\Mailer\Mailer;
use Symfony\Component\Mailer\Transport;
use Symfony\Component\Mime\Email;

require_once '../../vendor/autoload.php';
include_once '../../.data.php';

class MailBot
{

    public function sendEmail($to)
    {
        $email = (new Email())
            ->from('pocztazamowienauto@gmail.com')
            ->to('biuro@primakrakow.pl')
            ->subject('Rejestracja')
            ->text('Dziala');

        $credentials = new Credentials();
        $transport = Transport::fromDsn($credentials->getMailTransportGoogleDsn());

        $mailer = new Mailer($transport);

        $mailer->send($email);
    }
}