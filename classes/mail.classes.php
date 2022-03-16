<?php
// error_reporting(0);

include_once '../vendor/phpmailer/phpmailer/src/Exception.php';
include_once '../vendor/phpmailer/phpmailer/src/PHPMailer.php';
include_once '../vendor/autoload.php';

class MailBot
{
    public function sendEmail(string $to, string $attachmentPath): void
    {
        $mail = new PHPMailer\PHPMailer\PHPMailer(true);

        try {
            $mail->SMTPDebug = \PHPMailer\PHPMailer\SMTP::DEBUG_SERVER;
            $mail->isSMTP();
            $mail->Host = 'smtp.gmail.com';
            $mail->SMTPAuth = true;
            $mail->Username = 'pocztazamowienauto';
            $mail->Password = 'zaq12wsx';
            //$mail->SMTPSecure = PHPMailer::ENCRYPTION_SMTPS;
            $mail->Port = '465';

            $mail->setFrom('biuro@primakrako.pl', 'Zamówienia z platformy');
            // $mail->addAddress($to, ''); // TODO enable
            $mail->addAddress('biuro@primakrakow.pl', '');
            $mail->addReplyto('biuro@primakrakow.pl', 'PRIMA');

            //$mail->addAttachment($attachmentPath); // TODO enable

            $mail->isHTML(true);
            $mail->Subject = 'Zamówienie z platformy';
            $mail->Body = 'Otwórz załącznik';

            $mail->send();

        } catch (Exception $exception) {
            // TODO mail error to logs
            echo('---------------------------------------------------');
            echo($exception);
            echo('---------------------------------------------------');
            exit();
        }
    }
}