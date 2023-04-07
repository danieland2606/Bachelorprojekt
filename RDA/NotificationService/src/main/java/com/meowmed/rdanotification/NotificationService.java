package com.meowmed.rdanotification;

import ch.qos.logback.core.net.SyslogOutputStream;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

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


        try {
            String html = Files.readString(Path.of("./template/Customer.html"));

            mimeMessageHelper
                    = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(details.getEmail());
            mimeMessageHelper.setText("details.getMsgBody()");
            mimeMessageHelper.setSubject(subject);

            // Adding the attachment
            /*
            FileSystemResource file
                    = new FileSystemResource(
                    new File(details.getAttachment()));

            mimeMessageHelper.addAttachment(
                    file.getFilename(), file);
            */
            javaMailSender.send(mimeMessage);
            return "Mail sent Successfully";
        }


        catch (MessagingException e) {


            return "Error while sending mail!!!";
        }
        catch (IOException e){
            return "Couldn´t find File";
        }



    }

    public String policyNotification(MailPolicyEntity details) {
        MimeMessage mimeMessage
                = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        Map<String, Object> map= new HashMap<String, Object>();
        map.put("lastName", details.getFirstName());
        map.put("startDate", details.getStartDate());
        map.put("pid", details.getId());
        System.out.println();
        StringBuilder content = new StringBuilder();
        try {
            Path currentRelativePath = Paths.get("");
            String s = currentRelativePath.toAbsolutePath().toString();
            System.out.println("Current absolute path is: " + s);
            Path path = FileSystems.getDefault().getPath(".");
            BufferedReader in = new BufferedReader(new FileReader("./RDA/NotificationService/src/main/java/com/meowmed/rdanotification/template/Policy.html"));
            //RDA/NotificationService/src/main/java/com/meowmed/rdanotification/template/Policy.html
            String str;
            while ((str = in.readLine()) != null){
                content.append(str);
            }
            in.close();
            String message = content.toString();
            String html = content.toString();
            //Path fileName = Path.of("com/meowmed/rdanotification/template/Policy.html");
            //String html = Files.readString(fileName);
            //String message = String.format(html);
            message.formatted(map);
            System.out.println(message);
            /*
            mimeMessageHelper
                    = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(details.getEmail());
            mimeMessageHelper.setText(details.getMsgBody());
            mimeMessageHelper.setSubject("Vielen Dank für ihr Vertrauen in MeowMed+");
            */
    /*
            FileSystemResource file
                    = new FileSystemResource(
                    new File(details.getAttachment()));

            mimeMessageHelper.addAttachment(
                    file.getFilename(), file);
    */

            //javaMailSender.send(mimeMessage);
            return "Mail sent Successfully";
        }

        /*
        catch (MessagingException e) {

            return "Error while sending mail!!!";
        }
        */
        catch (IOException e){
            System.out.println(e.getMessage());
            return "Couldn´t find File";
        }


    }



    
}
