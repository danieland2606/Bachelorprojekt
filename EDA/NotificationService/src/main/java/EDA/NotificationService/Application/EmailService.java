package EDA.NotificationService.Application;

import EDA.NotificationService.Email.Email;
import EDA.NotificationService.Email.EmailFactory;
import EDA.NotificationService.Persistence.Entity.Customer;
import EDA.NotificationService.Persistence.Entity.Policy;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class for sending the Email of Events.
 */
@Service
public class EmailService {

    private final EmailSenderService emailSenderService;

    private final DatabaseService databaseService;

    private final EmailFactory emailFactory;
    private final String sender = "noreply@meowmed.com";

    @Autowired
    public EmailService(EmailSenderService emailSenderService, DatabaseService databaseService, EmailFactory emailFactory){
        this.emailSenderService = emailSenderService;
        this.databaseService = databaseService;
        this.emailFactory = emailFactory;
    }
    /**
     * Sends a customer created email to the specified customer.
     *
     * @param customer The representation of the customer to whom the email will be sent.
     */
    public void sendCustomerCreatedMail(Customer customer) {
        this.sendEmail(emailFactory.buildEmail(
                customer.getEmail(),
                sender,
                "Willkommen bei MeowMed!",
                "customernotification",
                customer
        ));
    }
    /**
     * Sends a customer changed email to the specified customer.
     *
     * @param customer The representation of the customer to whom the email will be sent.
     */
    public void sendCustomerChangedMail(Customer customer) {
        this.sendEmail(emailFactory.buildEmail(
                customer.getEmail(),
                sender,
                "Änderung der Angaben zu Ihrer Person",
                "customerchangednotification",
                customer
        ));
    }

    /**
     * Sends a policy created email to the customer associated with the specified policy.
     *
     * @param policy The representation of the policy for which the email will be sent.
     */
    public void sendPolicyCreatedMail(Policy policy) {
        this.sendEmail(emailFactory.buildEmail(
                policy.getCustomer().getEmail(),
                sender,
                "Ihre MeowMed Vertragsinformationen",
                "policynotification",
                policy
        ));
    }
    /**
     * Sends a policy changed email to the customer associated with the specified policy.
     *
     * @param policy The representation of the policy for which the email will be sent.
     */
    public void sendPolicyChangedMail(Policy policy) {
        this.sendEmail(emailFactory.buildEmail(
                policy.getCustomer().getEmail(),
                sender,
                "Ihre Vertragsänderungen",
                "policychangednotification",
                policy
        ));
    }
    /**
     * Sends a policy cancelled email to the customer associated with the specified policy.
     *
     * @param policy The representation of the policy for which the email will be sent.
     */
    public void sendPolicyCancelledMail(Policy policy) {
        this.sendEmail(emailFactory.buildEmail(
                policy.getCustomer().getEmail(),
                sender,
                "Kündigung Ihres Vertrages",
                "policycancellednotification",
                policy
        ));
    }

    /**
     * Sends the given email using the email sender service.
     *
     * @param email The email to be sent.
     */
    private void sendEmail(Email email) {
        try {
            emailSenderService.sendHtmlMessage(email);
        } catch (MessagingException e) {
            System.out.println("Fehler beim Senden der " + email.getTemplate() + " Mail, für" + email.getTo());
            e.printStackTrace();
        }
        System.out.println(email.getTemplate() + " Mail gesendet!");
    }
}