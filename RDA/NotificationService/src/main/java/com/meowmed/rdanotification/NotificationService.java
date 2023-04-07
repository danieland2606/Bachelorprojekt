package com.meowmed.rdanotification;

import com.meowmed.rdanotification.Email.MailCustomerEntity;
import com.meowmed.rdanotification.Email.MailPolicyEntity;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
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



    public ResponseEntity<String> customerNotification(MailCustomerEntity details) {
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(details.getEmail());
            mimeMessageHelper.setSubject("Vielen Dank für ihr Vertrauen in MeowMed+");
            Map<String,Object> properties = new HashMap<>();
            properties.put("formOfAdress", details.getFormOfAdress());
            properties.put("firstName", details.getFirstName());
            properties.put("lastName", details.getLastName());
            
            //properties.put("cid", details.getId());
            properties.put("martialStatus", details.getMartialStatus());
            properties.put("dateOfBirth", details.getDateOfBirth());
            properties.put("employmentStatus", details.getEmploymentStatus());
            properties.put("phoneNumber", details.getPhoneNumber());
            properties.put("bankDetails", details.getBankDetails());
            properties.put("adress", details.getAdress().getStreet() + ", " + details.getAdress().getPostalCode() + " " + details.getAdress().getCity());
            Context context = new Context();
            context.setVariables(properties);
            String html = templateEngine.process("customernotification.html", context);
            mimeMessageHelper.setText(html, true);
            /*
            FileSystemResource file
                    = new FileSystemResource(
                    new File(details.getAttachment()));

            mimeMessageHelper.addAttachment(
                    file.getFilename(), file);
            */
            javaMailSender.send(mimeMessage);
            return new ResponseEntity<String>("Mail wurde erfolgreich versendet",HttpStatusCode.valueOf(200));
        }
        catch (MessagingException e) {
            System.out.println(e);
        }
        return new ResponseEntity<String>("Fehler beim Versand",HttpStatusCode.valueOf(500));
    }

    public ResponseEntity<String> policyNotification(MailPolicyEntity details){
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
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
            if(details.isCastrated()){
                properties.put("castrated", "Ja");
            }else{
                properties.put("castrated", "Nein");
            }
            properties.put("personality", details.getPersonality());
            properties.put("environment", details.getEnvironment());
            properties.put("weight", details.getWeight());
            Context context = new Context();
            context.setVariables(properties);
            String html = templateEngine.process("policynotification.html", context);
            mimeMessageHelper.setText(html, true);
            /*
            FileSystemResource file
                    = new FileSystemResource(
                    new File(details.getAttachment()));

            mimeMessageHelper.addAttachment(
                    file.getFilename(), file);
            */
            javaMailSender.send(mimeMessage);
            return new ResponseEntity<String>("Mail wurde erfolgreich versendet",HttpStatusCode.valueOf(200));
        }catch(MessagingException e){
            System.out.println(e);
        }        
        return new ResponseEntity<String>("Fehler beim Versand",HttpStatusCode.valueOf(500));
       
    }
}
