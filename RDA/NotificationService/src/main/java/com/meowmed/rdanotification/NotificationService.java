package com.meowmed.rdanotification;

import com.meowmed.rdanotification.Email.MailCustomerEntity;
import com.meowmed.rdanotification.Email.MailPolicyEntity;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Diese Klasse ist die Service-Klasse des REST-Controllers 
 * Dependency-Injektion durch @RequiredArgsConstructor
 * @apiNote Die Schnittstelle ist in der Postman-Collection verzeichnet und steht in den Aufrufen
 * @author Alexander Hampel, Mozamil Ahmadzaei, Daniel Arnold
 * 
 */

@Service
@RequiredArgsConstructor
public class NotificationService {
	//Variable, initialisiert durch application.properties
    @Value("${spring.mail.username}")
    private String sender;

    //Variable, initialisiert durch application.properties, aktiviert die debugAusgaben
    @Value("${docker.debugmode}")
    private boolean debugmode;

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;


    /**
     * Diese Methode nimmt die Anfrage des REST-Controllers für den Customer-Emailversand und versendet diese
     * @param details Ein Customer-Objekt, dessen Inhalt in der EMail verwendet wird
     * @return Zurück kommt ein HTTP-Statuscode, der aussagt, ob die Mail versendet wurde
     */

    public ResponseEntity<String> customerNotification(MailCustomerEntity details) {
        try {
            RestTemplate template = new RestTemplate();
            ResponseEntity<String> responseEntity = template.getForEntity("https://catfact.ninja/fact", String.class);
            JSONObject catfact = new JSONObject(responseEntity.getBody());
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(details.getEmail());
            mimeMessageHelper.setSubject("Vielen Dank für ihr Vertrauen in MeowMed+");
            Map<String,Object> properties = new HashMap<>();
            properties.put("formOfAddress", details.getFormOfAddress());
            properties.put("firstName", details.getFirstName());
            properties.put("lastName", details.getLastName());
            properties.put("cid", details.getId());
            properties.put("martialStatus", details.getMaritalStatus());
            properties.put("dateOfBirth", details.getDateOfBirth());
            properties.put("employmentStatus", details.getEmploymentStatus());
            properties.put("phoneNumber", details.getPhoneNumber());
            properties.put("bankDetails", details.getBankDetails());
            properties.put("address", details.getStreet() + ", " + details.getPostalCode() + " " + details.getCity());
            if(details.isDogOwner()){
                properties.put("dogOwner", "Ja");
            }else{
                properties.put("dogOwner", "Nein");
            }
            properties.put("spruch","Katzenfakt: \"" + catfact.get("fact") + "\"");
            Context context = new Context();

            context.setVariables(properties);
            String html = templateEngine.process("customernotification.html", context);
            mimeMessageHelper.setText(html, true);
            emailSender.send(mimeMessage);
            return new ResponseEntity<String>("Mail wurde erfolgreich versendet",HttpStatusCode.valueOf(200));
        }
        catch (MessagingException e) {
            System.out.println(e);
        }
        return new ResponseEntity<String>("Fehler beim Versand",HttpStatusCode.valueOf(500));
    }


    /**
     * Diese Methode nimmt die Anfrage des REST-Controllers für den Customeränderung-Emailversand und versendet diese
     * @param details Ein Customer-Objekt, dessen Inhalt in der EMail verwendet wird
     * @return Zurück kommt ein HTTP-Statuscode, der aussagt, ob die Mail versendet wurde
     */
    public ResponseEntity<String> changeCustomerNotification(MailCustomerEntity details) {
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(details.getEmail());
            mimeMessageHelper.setSubject("Vielen Dank für ihr Vertrauen in MeowMed+");
            Map<String,Object> properties = new HashMap<>();

            properties.put("formOfAddress", details.getFormOfAddress());
            properties.put("firstName", details.getFirstName());
            properties.put("lastName", details.getLastName());

            properties.put("cid", details.getId());
            properties.put("martialStatus", details.getMaritalStatus());
            properties.put("dateOfBirth", details.getDateOfBirth());
            properties.put("employmentStatus", details.getEmploymentStatus());
            properties.put("phoneNumber", details.getPhoneNumber());
            properties.put("bankDetails", details.getBankDetails());
            properties.put("address", details.getStreet() + ", " + details.getPostalCode() + " " + details.getCity());
            if(details.isDogOwner()){
                properties.put("dogOwner", "Ja");
            }else{
                properties.put("dogOwner", "Nein");
            }
            Context context = new Context();
            context.setVariables(properties);
            String html = templateEngine.process("customerchangenotification.html", context);
            mimeMessageHelper.setText(html, true);
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
        if(debugmode) System.out.println("policyNotification: details: " + details);
        try {
            emailSender.send(createNotificationMessage(details, "create"));
            return new ResponseEntity<String>("Mail wurde erfolgreich versendet",HttpStatusCode.valueOf(200));
        }catch(MessagingException e){
            if(debugmode) System.out.println("policyNotification: MesaageException: " + e);
        }
        return new ResponseEntity<String>("Fehler beim Versand",HttpStatusCode.valueOf(500));

    }

    /**
     * Diese Methode nimmt die Anfrage des REST-Controllers für den Vertragsänderung-Emailversand und versendet diese
     * @param details Ein Vertrags-Objekt mit zusätzlichen Infos, dessen Inhalt in der EMail verwendet wird
     * @return Zurück kommt ein HTTP-Statuscode, der aussagt, ob die Mail versendet wurde
     */
    public ResponseEntity<String> changePolicyNotification(MailPolicyEntity details){
        if(debugmode) System.out.println("changePolicyNotification: details: " + details);
        try {
            emailSender.send(createNotificationMessage(details, "change"));
            return new ResponseEntity<String>("Mail wurde erfolgreich versendet",HttpStatusCode.valueOf(200));
        }catch(MessagingException e){
            if(debugmode) System.out.println("changePolicyNotification: MesaageException: " + e);
        }
        return new ResponseEntity<String>("Fehler beim Versand",HttpStatusCode.valueOf(500));

    }

    /**
     * Diese Methode nimmt die Anfrage des REST-Controllers für den Vertragslöschung-Emailversand und versendet diese
     * @param details Ein Customer-Objekt, dessen Inhalt in der EMail verwendet wird
     * @return Zurück kommt ein HTTP-Statuscode, der aussagt, ob die Mail versendet wurde
     */
    public ResponseEntity<String> deleteNotificationPolicy(MailPolicyEntity details){
        if(debugmode) System.out.println("deleteNotificationPolicy: details: " + details);
        try {
            emailSender.send(createNotificationMessage(details, "delete"));
            return new ResponseEntity<String>("Mail wurde erfolgreich versendet",HttpStatusCode.valueOf(200));
        }catch(MessagingException e){
            if(debugmode) System.out.println("deleteNotificationPolicy: MessagingException: " + e);
        }
        return new ResponseEntity<String>("Fehler beim Versand",HttpStatusCode.valueOf(500));
    }

    /**
     * Diese Methode erzeugt mir eine MimeMessage für den Policy-Mailversand abhängig der Nutzung
     * @param details Objekt, aus dem die Mailfelder erzeugt werden
     * @param usage Templatestring, wofür die Mail erzeugt wird
     * @return MimeMessage mit allen benötigten Daten  
     * @throws MessagingException Nested aus der MimeMessageHelper-Erzeugung
     */
    private MimeMessage createNotificationMessage(MailPolicyEntity details, String usage) throws MessagingException{
        if(debugmode) System.out.println("createNotificationMessage: MailPolicyEntity: " + details + " Nutzung: " + usage);
        String subject = "", template = "";
        Map<String,Object> properties = new HashMap<>();
        switch (usage) {
            case "create":
                subject = "Vielen Dank für ihr Vertrauen in MeowMed+";
                template = "policynotification.html";
                break;
            case "change":
                subject = "Änderung ihres Vertrages bei MeowMed+";
                template = "policychangenotification.html";
                break;
            case "delete":
                subject = "Änderung ihres Vertrages bei MeowMed+";
                template = "policydeletenotification.html";        
        }
        if(debugmode) System.out.println("createNotificationMessage: subject: " + subject + " template: " + template);
        switch (details.getFormOfAddress()){
            case "herr":
                properties.put("formOfAddress", "geehrter Herr");
                break;
            case "frau":
                properties.put("formOfAddress", "geehrte Frau");
                break;
            default:
                properties.put("formOfAddress", "geehrte/r " + details.getFormOfAddress());
                break;
        }
        properties.put("firstName", details.getFirstName());
        properties.put("lastName", details.getLastName());
        properties.put("pid", details.getPid());

        if(!usage.equalsIgnoreCase("delete")){
            properties.put("startDate", formatDate(details.getStartDate()));
            properties.put("endDate", formatDate(details.getEndDate()));
            properties.put("coverage", details.getCoverage());
            if(details.isCastrated()){
                properties.put("castrated", "Ja");
            }else{
                properties.put("castrated", "Nein");
            }
            properties.put("personality", details.getPersonality());
            properties.put("environment", details.getEnvironment());
            properties.put("weight", details.getWeight());
            properties.put("premium", details.getPremium());

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> responseEntity = restTemplate.getForEntity("https://api.exchangerate.host/latest?base=EUR", String.class);
            JSONObject exchangeMessage = new JSONObject(responseEntity.getBody());
            System.out.println(exchangeMessage);
            JSONObject exchangeRate = exchangeMessage.getJSONObject("rates");
            System.out.println(exchangeRate);            
            StringBuilder altPremium = new StringBuilder();
            altPremium.append("USD: ");
            altPremium.append(Math.round(details.getPremium()*exchangeRate.getDouble("USD")*100.0)/100.0);
            altPremium.append(" $, BTC: ");
            altPremium.append(details.getPremium()*exchangeRate.getDouble("BTC"));
            altPremium.append(" XBT, SAR: ");
            altPremium.append(Math.round(details.getPremium()*exchangeRate.getDouble("SAR")*100.0)/100.0);

            properties.put("premiumalternativ", altPremium.toString());
        }
        if(debugmode) System.out.println("createNotificationMessage: properties: " + properties);

        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        mimeMessageHelper.setFrom(sender);
        mimeMessageHelper.setTo(details.getEmail());
        mimeMessageHelper.setSubject(subject);
        Context context = new Context();
        context.setVariables(properties);
        String html = templateEngine.process(template, context);
        mimeMessageHelper.setText(html, true);  //Der schreibt das in mimeMessage?????
        if(debugmode) System.out.println("createNotificationMessage: mimeMessage: " + mimeMessage);
        return mimeMessage;
    }

    /**
     * Erzeugt ein deutsches Datumsformat
     * @param date LocalDate Variable
     * @return Formatierter String mit deutschem Datumsformat
     */
    private String formatDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return date.format(formatter);
    }

}