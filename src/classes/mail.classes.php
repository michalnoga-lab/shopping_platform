<?php
// error_reporting(0); // TODO

use Symfony\Component\Mailer\Exception\TransportExceptionInterface;
use Symfony\Component\Mailer\Mailer;
use Symfony\Component\Mailer\Transport;
use Symfony\Component\Mime\Email;

require_once '../../vendor/autoload.php';
include_once '../../.data.php';

class MailBot
{

    public function sendEmail($to, $activationLink)
    {
        try {
            $email = (new Email())
                ->from('biuro@primakrakow.pl')
                ->to('biuro@primakrakow.pl')
                // ->to($to) // TODO aktywować na produkcji
                ->subject('Rejestracja')
                ->text('Proszę potwierdzić założenie konta: ' . $activationLink);

            $credentials = new Credentials();
            $transport = Transport::fromDsn($credentials->getMailTransportGoogleDsn());

            $mailer = new Mailer($transport);

            $mailer->send($email);
        } catch (TransportExceptionInterface|Exception) {
            header('location: ../pages/register.php?info=email_error');
            exit();
        }
    }
}