package EDA.MeowMed.Application;

import events.customer.CustomerCreatedEvent;
import events.policy.PolicyChangedEvent;
import events.policy.PolicyCreatedEvent;
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
    private final String subjectCustomerCreated = "Willkommen bei MeowMed!";
    private final String templateCustomerCreated = "customernotification";
    private final String subjectCustomerTerminated = "Kündigung Ihres Vertrags bei MeowMed";
    private final String templateCustomerTerminated = "customertermination";

    private final String subjectPolicy = "Ihre MeowMed Vertragsinformationen";
    private final String templatePolicyCreated = "policynotification";

    private final String templatePolicyChanged = "policychangednotification";

    private final String subjectPolicyChanged = "Ihre Vertragsänderungen";

    /**
     * This method sends a customer created email using the provided CustomerCreatedEvent object.
     * It creates an Email object and sets its properties including "to", "from", "subject", "template",
     * and "properties".
     * The "properties" map and "to" email address are obtained from the CustomerCreatedEvent object.
     * "from", "subject" and "template" are all internally defined as final.
     * Which properties are needed for the email are defined in the "templateCustomerCreated".html file.
     * After all "properties" are filled the emailSenderService is called
     * to send the HTML message using the Email object.
     * If any exception occurs during the email sending process, it will be caught and printed
     * to the standard error stream.
     *
     * @param customerCreated the CustomerCreatedEvent containing all valuable data concerning the CustomerCreatedMail
     */
    public void sendCustomerCreatedMail(CustomerCreatedEvent customerCreated) {
        Email email = new Email();
        email.setTo(customerCreated.getEmail());
        email.setFrom(sender);
        email.setSubject(subjectCustomerCreated);
        email.setTemplate(templateCustomerCreated);
        Map<String, Object> properties = new HashMap<>();
        properties.put("formOfAddress", customerCreated.getFormOfAddress());
        properties.put("firstName", customerCreated.getFirstName());
        properties.put("lastName", customerCreated.getLastName());
        properties.put("cid", customerCreated.getId());
        properties.put("maritalStatus", customerCreated.getMaritalStatus());
        properties.put("dateOfBirth", customerCreated.getDateOfBirth());
        properties.put("employmentStatus", customerCreated.getEmploymentStatus());
        properties.put("phoneNumber", customerCreated.getPhoneNumber());
        properties.put("bankDetails", customerCreated.getBankDetails());
        properties.put("address", customerCreated.getAddress().toString());
        email.setProperties(properties);

        try {
            emailSenderService.sendHtmlMessage(email);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param customerCreated
     */
    public void sendCustomerTerminatedMail(CustomerCreatedEvent customerCreated) {
        Email email = new Email();
        email.setTo(customerCreated.getEmail());
        email.setFrom(sender);
        email.setSubject(subjectCustomerTerminated);
        email.setTemplate(templateCustomerTerminated);
        Map<String, Object> properties = new HashMap<>();
        properties.put("formOfAddress", customerCreated.getFormOfAddress());
        properties.put("firstName", customerCreated.getFirstName());
        properties.put("lastName", customerCreated.getLastName());
        email.setProperties(properties);

        try {
            emailSenderService.sendHtmlMessage(email);
        } catch (MessagingException e) {
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
     *
     * @param policyCreated the PolicyCreatedEvent containing all valuable data concerning the PolicyCreatedMail
     */
    public void sendPolicyCreatedMail(PolicyCreatedEvent policyCreated) {
        Email email = new Email();
        email.setTo(policyCreated.getCustomer().getEmail());
        email.setFrom(sender);
        email.setSubject(subjectPolicy);
        email.setTemplate(templatePolicyCreated);
        Map<String, Object> properties = new HashMap<>();
        properties.put("formOfAddress", policyCreated.getCustomer().getFormOfAddress());
        properties.put("firstName", policyCreated.getCustomer().getFirstName());
        properties.put("lastName", policyCreated.getCustomer().getLastName());
        properties.put("pid", policyCreated.getId());
        properties.put("startDate", policyCreated.getStartDate());
        properties.put("endDate", policyCreated.getEndDate());
        properties.put("coverage", policyCreated.getCoverage());
        properties.put("castrated", policyCreated.getObjectOfInsurance().isCastrated());
        properties.put("personality", policyCreated.getObjectOfInsurance().getPersonality());
        properties.put("environment", policyCreated.getObjectOfInsurance().getEnvironment());
        properties.put("weight", policyCreated.getObjectOfInsurance().getWeight());
        email.setProperties(properties);
        try {
            emailSenderService.sendHtmlMessage(email);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendPolicyChangedMail(PolicyChangedEvent policyChangedEvent) {
        Email email = new Email();
        email.setTo(policyChangedEvent.getCustomer().getEmail());
        email.setFrom(sender);
        email.setSubject(subjectPolicyChanged);
        email.setTemplate(templatePolicyChanged);
        Map<String, Object> properties = new HashMap<>();
        properties.put("formOfAddress", policyChangedEvent.getCustomer().getFormOfAddress());
        properties.put("firstName", policyChangedEvent.getCustomer().getFirstName());
        properties.put("lastName", policyChangedEvent.getCustomer().getLastName());
        properties.put("pid", policyChangedEvent.getPolicyID());
        properties.put("oldCoverage", policyChangedEvent.getOldCoverage());
        properties.put("newCoverage", policyChangedEvent.getNewCoverage());
        properties.put("oldPremium", policyChangedEvent.getOldPremium());
        properties.put("newPremium", policyChangedEvent.getNewPremium());
        email.setProperties(properties);
        try {
            emailSenderService.sendHtmlMessage(email);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}