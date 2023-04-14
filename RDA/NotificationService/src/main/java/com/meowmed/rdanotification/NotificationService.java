package com.meowmed.rdanotification;

import com.meowmed.rdanotification.Email.MailCustomerEntity;
import com.meowmed.rdanotification.Email.MailPolicyEntity;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Diese Klasse ist die Service-Klasse des REST-Controllers 
 * Dependency-Injektion durch @RequiredArgsConstructor
 * @apiNote Die Schnittstelle ist in der Postman-Collection verzeichnet und steht in den Aufrufen
 * @author Alexander Hampel, Mozamil Ahmadzaei
 * 
 */

@Service
@RequiredArgsConstructor
public class NotificationService {
	//Variable, initialisiert durch application.properties
    @Value("${spring.mail.username}")
    private String sender;

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;

    /**
     * Diese Methode nimmt die Anfrage des REST-Controllers für den Customer-Emailversand und versendet diese
     * @param details Ein Customer-Objekt, dessen Inhalt in der EMail verwendet wird
     * @return Zurück kommt ein HTTP-Statuscode, der aussagt, ob die Mail versendet wurde
     */

    public ResponseEntity<String> customerNotification(MailCustomerEntity details) {
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(details.getEmail());
            mimeMessageHelper.setSubject("Vielen Dank für ihr Vertrauen in MeowMed+");
            Map<String,Object> properties = new HashMap<>();
            properties.put("formOfAddress", details.getFormOfAdress());
            properties.put("firstName", details.getFirstName());
            properties.put("lastName", details.getLastName());
            
            //properties.put("cid", details.getId());
            properties.put("martialStatus", details.getMaritalStatus());
            properties.put("dateOfBirth", details.getDateOfBirth());
            properties.put("employmentStatus", details.getEmploymentStatus());
            properties.put("phoneNumber", details.getPhoneNumber());
            properties.put("bankDetails", details.getBankDetails());
            properties.put("address", details.getStreet() + ", " + details.getPostalCode() + " " + details.getCity());
            Context context = new Context();
            context.setVariables(properties);
            String html = templateEngine.process("customernotification.html", context);
            mimeMessageHelper.setText(html, true);
            mimeMessageHelper.addInline("myLogo", new File("/app/images/logo.png"));

            /*
            FileSystemResource file
                    = new FileSystemResource(
                    new File(details.getAttachment()));

            mimeMessageHelper.addAttachment(
                    file.getFilename(), file);
            */
            emailSender.send(mimeMessage);
            return new ResponseEntity<String>("Mail wurde erfolgreich versendet",HttpStatusCode.valueOf(200));
        }
        catch (MessagingException e) {
            System.out.println(e);
        }
        return new ResponseEntity<String>("Fehler beim Versand",HttpStatusCode.valueOf(500));
    }

    /**
     * Diese Methode nimmt die Anfrage des REST-Controllers für den Vertrags-Emailversand und versendet diese
     * @param details Ein Vertrags-Objekt mit zusätzlichen Infos, dessen Inhalt in der EMail verwendet wird
     * @return Zurück kommt ein HTTP-Statuscode, der aussagt, ob die Mail versendet wurde
     */

    public ResponseEntity<String> policyNotification(MailPolicyEntity details){
        System.out.println(details.toString());
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
            mimeMessageHelper.setText(html, true);  //Der schreibt das in mimeMessage?????
            /*
            FileSystemResource file
                    = new FileSystemResource(
                    new File(details.getAttachment()));

            mimeMessageHelper.addAttachment(
                    file.getFilename(), file);
            */
            emailSender.send(mimeMessage);
            return new ResponseEntity<String>("Mail wurde erfolgreich versendet",HttpStatusCode.valueOf(200));
        }catch(MessagingException e){
            System.out.println(e);
        }        
        return new ResponseEntity<String>("Fehler beim Versand",HttpStatusCode.valueOf(500));
       
    }
}
