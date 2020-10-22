package com.app.service;

import com.app.Utilities.CustomAddresses;
import com.app.exceptions.AppException;
import com.app.model.InfoCodes;
import com.app.model.LoggerInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class EmailService implements Runnable {

    //private final LoggerService loggerService;

    private final String to;
    private final String subject;
    private final String fileName;
    private final String pathToAttachment;

    @Override
    public void run() {
        sendEmail(to, subject, fileName, pathToAttachment);
    }

    public void sendEmail(String to, String subject, String fileName, String pathToAttachment) {
        try {
            if (to == null) {
                throw new AppException(InfoCodes.SERVICE_EMAIL, "sendEmail - filed to is null");
            }
            if (subject == null) {
                throw new AppException(InfoCodes.SERVICE_EMAIL, "sendEmail - filed subject is null");
            }
            if (pathToAttachment == null) {
                throw new AppException(InfoCodes.SERVICE_EMAIL, "sendEmail - filed pathToAttachment is null");
            }

            MimeMessage message = javaMailSender().createMimeMessage();

            try {
                MimeMessageHelper helper = new MimeMessageHelper(message, true);

                helper.setFrom(CustomAddresses.DEFAULT_EMAIL_SENDING_MAILBOX);
                helper.setTo(to);
                helper.setSubject(subject);
                helper.setText("OTWÓRZ ZAŁĄCZNIK");
                helper.addAttachment(fileName, new File(pathToAttachment));

            } catch (Exception e) {
                throw new AppException(InfoCodes.SERVICE_EMAIL, "sendEmail - error creating message");
            }

            javaMailSender().send(message);

            // // TODO: 07.10.2020 \https://www.baeldung.com/registration-verify-user-by-email

            TimeUnit.SECONDS.sleep(10); //// TODO: 07.10.2020 remove

//            loggerService.add(LoggerInfo.builder()
//                    .infoCode(InfoCodes.SERVICE_EMAIL)
//                    .message("email sent " + message.getFileName())
//                    .build());

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException(InfoCodes.SERVICE_EMAIL, "sendEmail - error sending email to: " + to);
        }
    }

    private JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setProtocol("smtp");
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("pocztazamowienauto@gmail.com");
        mailSender.setPassword("zaq12wsxcde3");

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        mailSender.setJavaMailProperties(properties);
        return mailSender;
    }

}