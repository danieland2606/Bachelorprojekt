package EDA.MeowMed.Application;

import events.customer.CustomerChangedEvent;
import events.customer.CustomerCreatedEvent;
import events.policy.PolicyChangedEvent;
import events.policy.PolicyCreatedEvent;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    private final String subjectCustomerCancelled = "Kündigung Ihres Vertrags bei MeowMed";
    private final String templateCustomerCancelled = "customercancellednotification";

    private final String subjectCustomerChanged = "Änderung der Angaben zu Ihrer Person";
    private final String templateCustomerChanged = "customerchangednotification";

    private final String subjectPolicy = "Ihre MeowMed Vertragsinformationen";
    private final String templatePolicyCreated = "policynotification";

    private final String templatePolicyChanged = "policychangednotification";
    private final String subjectPolicyChanged = "Ihre Vertragsänderungen";

    private final String templatePolicyCancelled = "policycancellednotification";
    private final String subjectPolicyCancelled = "Kündigung Ihres Vertrages";

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
        properties.put("dateOfBirth", formatDate(customerCreated.getDateOfBirth()));
        properties.put("dogOwner", boolToString(customerCreated.isDogOwner()));
        properties.put("employmentStatus", customerCreated.getEmploymentStatus());
        properties.put("phoneNumber", customerCreated.getPhoneNumber());
        properties.put("bankDetails", customerCreated.getBankDetails());
        properties.put("address", customerCreated.getAddress().toString());
        email.setProperties(properties);

        try {
            emailSenderService.sendHtmlMessage(email);
        } catch (MessagingException e) {
            System.out.println("Fehler beim Senden der CustomerCreated Mail");
            e.printStackTrace();
        }
        System.out.println("CustomerCreated Mail gesendet!");
    }

    public void sendCustomerChangedMail(CustomerChangedEvent customerChanged) {
        Email email = new Email();
        email.setTo(customerChanged.getNewCustomer().getEmail());
        email.setFrom(sender);
        email.setSubject(subjectCustomerChanged);
        email.setTemplate(templateCustomerChanged);
        Map<String, Object> properties = new HashMap<>();
        properties.put("formOfAddress", customerChanged.getNewCustomer().getFormOfAddress());
        properties.put("firstName", customerChanged.getNewCustomer().getFirstName());
        properties.put("lastName", customerChanged.getNewCustomer().getLastName());
        properties.put("cid", customerChanged.getNewCustomer().getId());
        properties.put("maritalStatus", customerChanged.getNewCustomer().getMaritalStatus());
        properties.put("dateOfBirth", formatDate(customerChanged.getNewCustomer().getDateOfBirth()));
        properties.put("dogOwner", boolToString(customerChanged.getNewCustomer().getDogOwner()));
        properties.put("employmentStatus", customerChanged.getNewCustomer().getEmploymentStatus());
        properties.put("phoneNumber", customerChanged.getNewCustomer().getPhoneNumber());
        properties.put("bankDetails", customerChanged.getNewCustomer().getBankDetails());
        properties.put("address", customerChanged.getNewCustomer().getAddress().toString());
        email.setProperties(properties);

        try {
            emailSenderService.sendHtmlMessage(email);
        } catch (MessagingException e) {
            System.out.println("Fehler beim Senden der CustomerChanged Mail");
            e.printStackTrace();
        }
        System.out.println("CustomerChanged Mail gesendet!");
    }

    /**
     *
     * @param customerChanged
     */
    public void sendCustomerCancelledMail(CustomerChangedEvent customerChanged) {
        Email email = new Email();
        email.setTo(customerChanged.getNewCustomer().getEmail());
        email.setFrom(sender);
        email.setSubject(subjectCustomerCancelled);
        email.setTemplate(templateCustomerCancelled);
        Map<String, Object> properties = new HashMap<>();
        properties.put("formOfAddress", customerChanged.getNewCustomer().getFormOfAddress());
        properties.put("firstName", customerChanged.getNewCustomer().getFirstName());
        properties.put("lastName", customerChanged.getNewCustomer().getLastName());
        email.setProperties(properties);

        try {
            emailSenderService.sendHtmlMessage(email);
        } catch (MessagingException e) {
            System.out.println("Fehler beim Senden der CustomerCancelled Mail");
            e.printStackTrace();
        }
        System.out.println("CustomerCancelled Mail gesendet!");
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
        email.setTo(policyCreated.getPolicy().getCustomer().getEmail());
        email.setFrom(sender);
        email.setSubject(subjectPolicy);
        email.setTemplate(templatePolicyCreated);
        Map<String, Object> properties = new HashMap<>();
        properties.put("formOfAddress", policyCreated.getPolicy().getCustomer().getFormOfAddress());
        properties.put("firstName", policyCreated.getPolicy().getCustomer().getFirstName());
        properties.put("lastName", policyCreated.getPolicy().getCustomer().getLastName());
        properties.put("pid", policyCreated.getPolicy().getId());
        properties.put("startDate", formatDate(policyCreated.getPolicy().getStartDate()));
        properties.put("endDate", formatDate(policyCreated.getPolicy().getEndDate()));
        properties.put("coverage", policyCreated.getPolicy().getCoverage());
        properties.put("castrated", boolToString(policyCreated.getPolicy().getObjectOfInsurance().isCastrated()));
        properties.put("personality", policyCreated.getPolicy().getObjectOfInsurance().getPersonality());
        properties.put("environment", policyCreated.getPolicy().getObjectOfInsurance().getEnvironment());
        properties.put("weight", policyCreated.getPolicy().getObjectOfInsurance().getWeight());
        properties.put("premium", policyCreated.getPolicy().getPremium());
        email.setProperties(properties);
        try {
            emailSenderService.sendHtmlMessage(email);
        } catch (MessagingException e) {
            System.out.println("Fehler beim Senden der PolicyCreated Mail");
            e.printStackTrace();
        }
        System.out.println("PolicyCreated Mail gesendet!");
    }

    public void sendPolicyChangedMail(PolicyChangedEvent policyChangedEvent) {
        Email email = new Email();
        email.setTo(policyChangedEvent.getNewPolicy().getCustomer().getEmail());
        email.setFrom(sender);
        email.setSubject(subjectPolicyChanged);
        email.setTemplate(templatePolicyChanged);
        Map<String, Object> properties = new HashMap<>();
        properties.put("formOfAddress", policyChangedEvent.getNewPolicy().getCustomer().getFormOfAddress());
        properties.put("firstName", policyChangedEvent.getNewPolicy().getCustomer().getFirstName());
        properties.put("lastName", policyChangedEvent.getNewPolicy().getCustomer().getLastName());
        properties.put("pid", policyChangedEvent.getNewPolicy().getId());
        properties.put("oldCoverage", policyChangedEvent.getOldPolicy().getCoverage());
        properties.put("newCoverage", policyChangedEvent.getNewPolicy().getCoverage());
        properties.put("oldPremium", policyChangedEvent.getOldPolicy().getPremium());
        properties.put("newPremium", policyChangedEvent.getNewPolicy().getPremium());
        email.setProperties(properties);
        try {
            emailSenderService.sendHtmlMessage(email);
        } catch (MessagingException e) {
            System.out.println("Fehler beim Senden der PolicyChanged Mail");
            e.printStackTrace();
        }
        System.out.println("PolicyChanged Mail gesendet!");
    }

    public void sendPolicyCancelledMail(PolicyChangedEvent policyChangedEvent) {
        Email email = new Email();
        email.setTo(policyChangedEvent.getNewPolicy().getCustomer().getEmail());
        email.setFrom(sender);
        email.setSubject(subjectPolicyCancelled);
        email.setTemplate(templatePolicyCancelled);
        Map<String, Object> properties = new HashMap<>();
        properties.put("formOfAddress", policyChangedEvent.getNewPolicy().getCustomer().getFormOfAddress());
        properties.put("firstName", policyChangedEvent.getNewPolicy().getCustomer().getFirstName());
        properties.put("lastName", policyChangedEvent.getNewPolicy().getCustomer().getLastName());
        properties.put("pid", policyChangedEvent.getNewPolicy().getId());
        properties.put("startDate", formatDate(policyChangedEvent.getNewPolicy().getStartDate()));
        properties.put("endDate", formatDate(policyChangedEvent.getNewPolicy().getEndDate()));
        properties.put("coverage", policyChangedEvent.getNewPolicy().getCoverage());
        properties.put("castrated", boolToString(policyChangedEvent.getNewPolicy().getObjectOfInsurance().isCastrated()));
        properties.put("personality", policyChangedEvent.getNewPolicy().getObjectOfInsurance().getPersonality());
        properties.put("environment", policyChangedEvent.getNewPolicy().getObjectOfInsurance().getEnvironment());
        properties.put("weight", policyChangedEvent.getNewPolicy().getObjectOfInsurance().getWeight());
        properties.put("premium", policyChangedEvent.getNewPolicy().getPremium());
        email.setProperties(properties);
        try {
            emailSenderService.sendHtmlMessage(email);
        } catch (MessagingException e) {
            System.out.println("Fehler beim Senden der PolicyCancelled Mail");
            e.printStackTrace();
        }
        System.out.println("PolicyCancelled Mail gesendet!");
    }

    //FIXME Bereits in Eventklassen machen?
    private String boolToString (boolean bool) {
        if (bool)
            return "Ja";
        return "Nein";
    }

    private String formatDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return date.format(formatter);
    }
}