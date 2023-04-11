package EDA.MeowMed.Application;

import EDA.MeowMed.Messaging.EventObjects.CustomerCreatedEvent;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class NotificationService {

    @Autowired
    private EmailSenderService emailSenderService;
    private final String sender = "noreply@meowmed.com";
    private final String subjectCustomer = "Willkommen bei Meowmed!";
    private final String templateCustomer = "customernotification";
    private final String subjectPolicy = "Willkommen bei Meowmed!";
    private final String templatePolicy = "policynotification";
    /**
     *
     * @param customerCreated
     */
    public void sendCustomerCreatedMail(CustomerCreatedEvent customerCreated) {
        Email email = new Email();
        email.setTo(customerCreated.getEmail());
        email.setFrom(sender);
        email.setSubject(subjectCustomer);
        email.setTemplate(templateCustomer);
        Map<String, Object> properties = new HashMap<>();
        properties.put("formOfAddress",customerCreated.getFormOfAddress());
        properties.put("firstName",customerCreated.getFirstName());
        properties.put("lastName",customerCreated.getLastName());
        properties.put("cid", customerCreated.getId());
        properties.put("maritalStatus",  customerCreated.getMaritalStatus());
        properties.put("dateOfBirth", customerCreated.getDateOfBirth());
        properties.put("employmentStatus", customerCreated.getEmploymentStatus());
        properties.put("phoneNumber", customerCreated.getPhoneNumber());
        properties.put("bankDetails", customerCreated.getBankDetails());
        properties.put("address", customerCreated.getAddress().toString());
        email.setProperties(properties);

        try {
            emailSenderService.sendHtmlMessage(email);
        } catch (MessagingException e){
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