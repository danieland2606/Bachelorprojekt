package EDA.MeowMed.Application;

import EDA.MeowMed.Messaging.EventObjects.CustomerCreatedEvent;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class NotificationService {

    @Autowired
    private JavaMailSender mailSender;
    private final String sender = "noreply@meowmed.paw";
    private final String subject = "Willkommen bei Meowmed!";

    public void sendCustomerCreatedMail(CustomerCreatedEvent newCustomer) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {

            // Setting multipart as true for attachments to
            // be send
            mimeMessageHelper
                    = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(newCustomer.getEmail());
            mimeMessageHelper.setText("Test");
            mimeMessageHelper.setSubject(subject);

            // Adding the attachment
//            FileSystemResource file
//                    = new FileSystemResource(
//                    new File(details.getAttachment()));
//
//            mimeMessageHelper.addAttachment(
//                    file.getFilename(), file);

            // Sending the mail
            mailSender.send(mimeMessage);
            return;
        }

        // Catch block to handle MessagingException
        catch (MessagingException e) {
            e.printStackTrace();
        }
    }

//    public void sendPolicyCreatedMail(NewPolicyEvent newPolicy) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("noreply@meowmed.com");
//        message.setTo(to);
//        message.setSubject(subject);
//        message.setText(text);
//        mailSender.send(message);
//    }

}