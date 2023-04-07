package com.meowmed.rdanotification;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.meowmed.rdanotification.Email.MailCustomerEntity;
import com.meowmed.rdanotification.Email.MailPolicyEntity;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String sender;

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;



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

    public String policyNotification(MailPolicyEntity details){
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        System.out.println(details);
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(details.getEmail());
            mimeMessageHelper.setSubject("Vielen Dank für ihr Vertrauen in MeowMed+");
            Map<String,Object> properties = new HashMap<>();
            properties.put("formOfAdress", details.getFormOfAdress());
            properties.put("firstName", details.getFirstName());
            properties.put("lastName", details.getLastName());
            properties.put("pid", details.getPid());
            properties.put("startDate", details.getStartDate());
            properties.put("endDate", details.getEndDate());
            properties.put("coverage", details.getCoverage());
            properties.put("castrated", details.isCastrated());
            properties.put("personality", details.getPersonality());
            properties.put("environment", details.getEnvironment());
            properties.put("weight", details.getWeight());
            Context context = new Context();
            context.setVariables(properties);
            String html = templateEngine.process("policynotification.html", context);
            mimeMessageHelper.setText(html, true);

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

            javaMailSender.send(mimeMessage);
            return "Mail sent Successfully";
        }catch(Exception e){
            System.out.println(e);
        }
        return "Mail sent Successfully";
        /*
        catch (MessagingException e) {

            return "Error while sending mail!!!";
        }
        */
        
    }



    
}
