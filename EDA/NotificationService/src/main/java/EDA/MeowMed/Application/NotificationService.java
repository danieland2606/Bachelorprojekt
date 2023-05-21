package EDA.MeowMed.Application;

import EDA.MeowMed.Email.Email;
import EDA.MeowMed.Email.EmailFactory;
import events.customer.CustomerChangedEvent;
import events.customer.CustomerCreatedEvent;
import events.policy.PolicyChangedEvent;
import events.policy.PolicyCreatedEvent;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

    private final String subjectPolicyCreated = "Ihre MeowMed Vertragsinformationen";
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
        Email email = emailFactory.buildEmail(
                customerCreated.getEmail(),
                sender,
                subjectCustomerCreated,
                templateCustomerCreated,
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
        Email email = emailFactory.buildEmail(
                customerChanged.getEmail(),
                sender,
                subjectCustomerChanged,
                templateCustomerChanged,
                customerChanged
        );
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
        Email email = emailFactory.buildEmail(
                customerChanged.getEmail(),
                sender,
                subjectCustomerCancelled,
                templateCustomerCancelled,
                customerChanged
        );
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
        Email email = emailFactory.buildEmail(
                policyCreated.getEmail(),
                sender,
                subjectPolicyCreated,
                templatePolicyCreated,
                policyCreated
        );
        try {
            emailSenderService.sendHtmlMessage(email);
        } catch (MessagingException e) {
            System.out.println("Fehler beim Senden der PolicyCreated Mail");
            e.printStackTrace();
        }
        System.out.println("PolicyCreated Mail gesendet!");
    }

    public void sendPolicyChangedMail(PolicyChangedEvent policyChangedEvent) {
        Email email = emailFactory.buildEmail(
                policyChangedEvent.getEmail(),
                sender,
                subjectPolicyChanged,
                templatePolicyChanged,
                policyChangedEvent
        );
        try {
            emailSenderService.sendHtmlMessage(email);
        } catch (MessagingException e) {
            System.out.println("Fehler beim Senden der PolicyChanged Mail");
            e.printStackTrace();
        }
        System.out.println("PolicyChanged Mail gesendet!");
    }

    public void sendPolicyCancelledMail(PolicyChangedEvent policyChangedEvent) {
        Email email = emailFactory.buildEmail(
                policyChangedEvent.getEmail(),
                sender,
                subjectPolicyCancelled,
                templatePolicyCancelled,
                policyChangedEvent
        );
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