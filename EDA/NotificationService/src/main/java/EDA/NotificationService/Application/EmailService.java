package EDA.NotificationService.Application;

import EDA.NotificationService.Email.Email;
import EDA.NotificationService.Email.EmailFactory;
import EDA.NotificationService.Persistence.Entity.Customer;
import event.objects.policy.PolicyEvent;
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
        this.emailSenderService =emailSenderService;
        this.databaseService = databaseService;
        this.emailFactory =emailFactory;
    }

    /**
     *
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
     *
     */
    public void sendPolicyCreatedMail(PolicyEvent policyEvent) {
        this.sendEmail(emailFactory.buildEmail(
                "",//todo policyEvent.getEmail(),
                sender,
                "Ihre MeowMed Vertragsinformationen",
                "policynotification",
                policyEvent
        ));
    }

    public void sendPolicyChangedMail(PolicyEvent policyEvent) {
        this.sendEmail(emailFactory.buildEmail(
                "",//todo policyEvent.getEmail(),
                sender,
                "Ihre Vertragsänderungen",
                "policychangednotification",
                policyEvent
        ));
    }

    public void sendPolicyCancelledMail(PolicyEvent policyEvent) {
        this.sendEmail(emailFactory.buildEmail(
                "",//todo policyEvent.getEmail(),
                sender,
                "Kündigung Ihres Vertrages",
                "policycancellednotification",
                policyEvent
        ));
    }

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