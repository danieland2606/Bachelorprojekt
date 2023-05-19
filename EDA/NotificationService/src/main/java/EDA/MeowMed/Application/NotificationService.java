package EDA.MeowMed.Application;

import events.customer.CustomerChangedEvent;
import events.customer.CustomerCreatedEvent;
import events.customer.subclasses.Address;
import events.customer.subclasses.CustomerData;
import events.policy.PolicyChangedEvent;
import events.policy.PolicyCreatedEvent;
import events.policy.subclasses.CustomerPojo;
import events.policy.subclasses.PolicyPojo;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Class for sending the Email of CustomerCreatedEvent and PolicyCreatedEvent.
 */
@Service
public class NotificationService {

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private EmailFactory emailFactory;
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
//        Email email = setupMail(customerCreated.getEmail(), sender, subjectCustomerCreated, templateCustomerCreated);
//        Map<String, Object> properties = new HashMap<>();
//        properties.put("formOfAddress", formatFormOfAddress(customerCreated.getFormOfAddress()));
//        properties.put("firstName", customerCreated.getFirstName());
//        properties.put("lastName", customerCreated.getLastName());
//        properties.put("cid", customerCreated.getId());
//        properties.put("maritalStatus", customerCreated.getMaritalStatus());
//        properties.put("dateOfBirth", formatDate(customerCreated.getDateOfBirth()));
//        properties.put("dogOwner", boolToString(customerCreated.isDogOwner()));
//        properties.put("employmentStatus", customerCreated.getEmploymentStatus());
//        properties.put("phoneNumber", customerCreated.getPhoneNumber());
//        properties.put("bankDetails", customerCreated.getBankDetails());
//        properties.put("address", customerCreated.getAddress().toString());
//        email.setProperties(properties);
        Map<String, Function<Object, String>> parser = new HashMap<>();
        parser.put("formOfAddress", NotificationService::formatFormOfAddress);
        parser.put("dateOfBirth", NotificationService::formatDate);
        parser.put("dogOwner", NotificationService::boolToString);
        parser.put("address", Object::toString);
        Email email = emailFactory.buildEmail(
                customerCreated.getEmail(),
                sender,
                subjectCustomerCreated,
                templateCustomerCreated,
                parser,
                customerCreated
        );

        try {
            emailSenderService.sendHtmlMessage(email);
        } catch (MessagingException e) {
            System.out.println("Fehler beim Senden der CustomerCreated Mail");
            e.printStackTrace();
        }
        System.out.println("CustomerCreated Mail gesendet!");
    }

    public void sendCustomerChangedMail(CustomerChangedEvent customerChanged) {
        CustomerData customerData = customerChanged.getNewCustomer();
        Email email = setupMail(customerData.getEmail(), sender, subjectCustomerChanged, templateCustomerChanged);
        Map<String, Object> properties = new HashMap<>();
        properties.put("formOfAddress", formatFormOfAddress(customerChanged.getNewCustomer().getFormOfAddress()));
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
     * @param customerChanged
     */
    public void sendCustomerCancelledMail(CustomerChangedEvent customerChanged) {
        CustomerData customerData = customerChanged.getNewCustomer();
        Email email = setupMail(customerData.getEmail(), sender, subjectCustomerCancelled, templateCustomerCancelled);
        Map<String, Object> properties = new HashMap<>();
        properties.put("formOfAddress", formatFormOfAddress(customerChanged.getNewCustomer().getFormOfAddress()));
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
        PolicyPojo policyData = policyCreated.getPolicy();
        CustomerPojo customerData = policyData.getCustomer();
        Email email = setupMail(customerData.getEmail(), sender, subjectPolicy, templatePolicyCreated);
        Map<String, Object> properties = new HashMap<>();
        properties.put("formOfAddress", formatFormOfAddress(customerData.getFormOfAddress()));
        properties.put("firstName", customerData.getFirstName());
        properties.put("lastName", customerData.getLastName());
        properties.put("pid", policyData.getId());
        properties.put("startDate", formatDate(policyData.getStartDate()));
        properties.put("endDate", formatDate(policyData.getEndDate()));
        properties.put("coverage", policyData.getCoverage());
        properties.put("castrated", boolToString(policyData.getObjectOfInsurance().isCastrated()));
        properties.put("personality", policyData.getObjectOfInsurance().getPersonality());
        properties.put("environment", policyData.getObjectOfInsurance().getEnvironment());
        properties.put("weight", policyData.getObjectOfInsurance().getWeight());
        properties.put("premium", policyData.getPremium());
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
        PolicyPojo newPolicy = policyChangedEvent.getNewPolicy();
        PolicyPojo odlPolicy = policyChangedEvent.getOldPolicy();
        Email email = setupMail(newPolicy.getCustomer().getEmail(), sender, subjectPolicyChanged,
                templatePolicyChanged);
        Map<String, Object> properties = new HashMap<>();
        properties.put("formOfAddress", formatFormOfAddress(newPolicy.getCustomer().getFormOfAddress()));
        properties.put("firstName", newPolicy.getCustomer().getFirstName());
        properties.put("lastName", newPolicy.getCustomer().getLastName());
        properties.put("pid", newPolicy.getId());
        properties.put("oldCoverage", odlPolicy.getCoverage());
        properties.put("newCoverage", newPolicy.getCoverage());
        properties.put("oldPremium", odlPolicy.getPremium());
        properties.put("newPremium", newPolicy.getPremium());
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
        PolicyPojo policyData = policyChangedEvent.getNewPolicy();
        CustomerPojo customerData = policyData.getCustomer();
        Email email = setupMail(customerData.getEmail(), sender, subjectCustomerCancelled, templatePolicyCancelled);
        Map<String, Object> properties = new HashMap<>();
        properties.put("formOfAddress", formatFormOfAddress(customerData.getFormOfAddress()));
        properties.put("firstName", customerData.getFirstName());
        properties.put("lastName", customerData.getLastName());
        properties.put("pid", policyData.getId());
        properties.put("startDate", formatDate(policyData.getStartDate()));
        properties.put("endDate", formatDate(policyData.getEndDate()));
        properties.put("coverage", policyData.getCoverage());
        properties.put("castrated", boolToString(policyData.getObjectOfInsurance().isCastrated()));
        properties.put("personality", policyData.getObjectOfInsurance().getPersonality());
        properties.put("environment", policyData.getObjectOfInsurance().getEnvironment());
        properties.put("weight", policyData.getObjectOfInsurance().getWeight());
        properties.put("premium", policyData.getPremium());
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
    private static String boolToString(Object bool) {
        if ((boolean) bool)
            return "Ja";
        return "Nein";
    }

    private static String formatDate(Object date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return ((LocalDate) date).format(formatter);
    }

    public static String formatFormOfAddress(Object formOfAddress) {

        if (formOfAddress.equals("herr"))
            return "Sehr geehrter Herr ";
        else
            return "Sehr geehrte Frau ";
    }

    private Email setupMail(String to, String from, String subject, String template) {
        Email mail = new Email();
        mail.setFrom(from);
        mail.setTo(to);
        mail.setSubject(subject);
        mail.setTemplate(template);
        return mail;
    }
}