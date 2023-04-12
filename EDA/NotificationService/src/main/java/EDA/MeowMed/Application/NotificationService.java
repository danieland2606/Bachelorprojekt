package EDA.MeowMed.Application;

import EDA.MeowMed.Messaging.EventObjects.CustomerCreatedEvent;

import EDA.MeowMed.Messaging.EventObjects.PolicyCreatedEvent;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Class for sending the Email of CustomerCreatedEvent and PolicyCreatedEvent.
 */
@Service
public class NotificationService {

    @Autowired
    private EmailSenderService emailSenderService;
    private final String sender = "noreply@meowmed.com";
    private final String subjectCustomer = "Willkommen bei Meowmed!";
    private final String templateCustomer = "customernotification";
    private final String subjectPolicy = ""; //ToDo: add valid Subject for Policy.
    private final String templatePolicy = "policynotification";

    /**
     * This method sends a customer created email using the provided CustomerCreatedEvent object.
     * It creates an Email object and sets its properties including "to", "from", "subject", "template",
     * and "properties".
     * The "properties" map and "to" email address are obtained from the CustomerCreatedEvent object.
     * "from", "subject" and "template" are all internally defined as final.
     * Which properties are needed for the email are defined in the "templateCustomer".html file.
     * After all "properties" are filled the emailSenderService is called
     * to send the HTML message using the Email object.
     * If any exception occurs during the email sending process, it will be caught and printed
     * to the standard error stream.
     * @param customerCreated the CustomerCreatedEvent containing all valuable data concerning the CustomerCreatedMail
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

    /**
     * This method sends a policy created email using the provided PolicyCreatedEvent object.
     * It creates an Email object and sets its properties including "to", "from", "subject", "template",
     * and "properties".
     * The "properties" map and "to" email address are obtained from the PolicyCreatedEvent object.
     * "from", "subject" and "template" are all internally defined as final.
     * Which properties are needed for the email are defined in the "templatePolicy".html file.
     * After all "properties" are filled the emailSenderService is called
     * to send the HTML message using the Email object.
     * If any exception occurs during the email sending process, it will be caught and printed
     * to the standard error stream.
     * @param policyCreated the PolicyCreatedEvent containing all valuable data concerning the PolicyCreatedMail
     */
    public void sendPolicyCreatedMail(PolicyCreatedEvent policyCreated) {
        Email email = new Email();
        email.setTo(policyCreated.getCustomer().getEmail());
        email.setFrom(sender);
        email.setSubject(subjectPolicy);
        email.setTemplate(templatePolicy);
        Map<String, Object> properties = new HashMap<>();
        properties.put("formOfAddress",policyCreated.getCustomer().getFormOfAddress());
        properties.put("firstName",policyCreated.getCustomer().getFirstName());
        properties.put("lastName",policyCreated.getCustomer().getLastName());
        properties.put("pid", policyCreated.getId());
        properties.put("startDate", policyCreated.getStartDate());
        properties.put("endDate",policyCreated.getEndDate());
        properties.put("coverage", policyCreated.getCoverage());
        properties.put("castrated", policyCreated.getObjectOfInsurance().isCastrated());
        properties.put("personality", policyCreated.getObjectOfInsurance().getPersonality());
        properties.put("environment", policyCreated.getObjectOfInsurance().getEnvironment());
        properties.put("weight", policyCreated.getObjectOfInsurance().getWeight());
        email.setProperties(properties);

        try {
            emailSenderService.sendHtmlMessage(email);
        } catch (MessagingException e){
            e.printStackTrace();
        }
    }

}