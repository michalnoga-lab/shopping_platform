<?php
// error_reporting(0); // TODO

use Symfony\Component\Mailer\Exception\TransportExceptionInterface;
use Symfony\Component\Mailer\Transport;
use Symfony\Component\Mime\Email;

//include_once '../includes/logger.inc.php';

//use Symfony\Component\Mime\Email;
//use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
//use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Mailer\MailerInterface;

//use Symfony\Component\Routing\Annotation\Route;


//class MailBot extends AbstractController
class MailBot
{
    /**
     * @throws TransportExceptionInterface
     */
    public function sendEmail($to)
    {
        $text = 'HELLO';

        $email = (new Email())
            ->from('pocztazamowienauto@gmail.com')
            ->to('biuro@primakrakow.pl')
            ->subject('Potwierdzenie rejestracji')
            ->text($text);

        $transport = Transport::fromDsn('gmail+smtp://pocztazamowienauto:zaq12wsxcde3@default');

        $mailer = new \Symfony\Component\Mailer\Mailer($transport);

        $mailer->send($email);
    }
}