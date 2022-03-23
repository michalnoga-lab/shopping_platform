<?php
// error_reporting(0);

use Symfony\Component\Mailer\Mailer;
use Symfony\Component\Mailer\Transport;

//include_once '../vendor/autoload.php';

class MailBot
{
    public function sendEmail($to): void
    {
        try {
            $email = (new \Symfony\Component\Mime\Email())
                ->from('biuro@primakrakow.pl')
                ->to($to)
                ->addBcc('biuro@primakrakow.pl')
                ->subject('Rejestracja')
                ->html('
                <p>Kliknij <a></a> aby potwierdzić rejestrację na platformie</p>
                ');

            $dsn = 'gmail+smtp://pocztazamowienauto:zaq12wsxcde3@default';
            $transport = Transport::fromDsn($dsn);
            $mailer = new Mailer($transport);
            $mailer->send($email);
        } catch (Exception $e) {
            // TODO handle exception
        } catch (\Symfony\Component\Mailer\Exception\TransportExceptionInterface $e) {
            // TODO handle exception
        }
    }
}