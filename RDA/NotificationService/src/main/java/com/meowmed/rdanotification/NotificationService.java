package com.meowmed.rdanotification;

import com.meowmed.rdanotification.Email.MailCustomerEntity;
import com.meowmed.rdanotification.Email.MailPolicyEntity;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class NotificationService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String sender;

    public NotificationService() {}


    public String customerNotification(MailCustomerEntity details) {


        MimeMessage mimeMessage
                = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
        String subject= "Willkommen bei Moewmed+";
        String html = Files.readString(Path.of("./template/Customer.html"));

        try {


            mimeMessageHelper
                    = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(details.getEmail());
            mimeMessageHelper.setText(details.getMsgBody());
            mimeMessageHelper.setSubject(subject);

            // Adding the attachment
            FileSystemResource file
                    = new FileSystemResource(
                    new File(details.getAttachment()));

            mimeMessageHelper.addAttachment(
                    file.getFilename(), file);

            javaMailSender.send(mimeMessage);
            return "Mail sent Successfully";
        }


        catch (MessagingException e) {


            return "Error while sending mail!!!";
        }


    }

    public String policyNotification(MailPolicyEntity details) {
        MimeMessage mimeMessage
                = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {


            mimeMessageHelper
                    = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(details.getRecipient());
            mimeMessageHelper.setText(details.getMsgBody());
            mimeMessageHelper.setSubject(
                    details.getSubject());


            FileSystemResource file
                    = new FileSystemResource(
                    new File(details.getAttachment()));

            mimeMessageHelper.addAttachment(
                    file.getFilename(), file);


            javaMailSender.send(mimeMessage);
            return "Mail sent Successfully";
        }


        catch (MessagingException e) {

            return "Error while sending mail!!!";
        }


    }



    
}
