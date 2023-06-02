package EDA.MeowMed.Application;

import EDA.MeowMed.Email.Email;
import EDA.MeowMed.Email.EmailFactory;
import event.objects.customer.CustomerChangedEvent;
import event.objects.customer.CustomerCreatedEvent;
import event.objects.policy.PolicyChangedEvent;
import event.objects.policy.PolicyCreatedEvent;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    /**
     *
     */
    public void sendCustomerCreatedMail(CustomerCreatedEvent customerCreated) {
        this.sendEmail(emailFactory.buildEmail(
                customerCreated.getEmail(),
                sender,
                "Willkommen bei MeowMed!",
                "customernotification",
                customerCreated
        ));
    }

    public void sendCustomerChangedMail(CustomerChangedEvent customerChanged) {
        this.sendEmail(emailFactory.buildEmail(
                customerChanged.getEmail(),
                sender,
                "Änderung der Angaben zu Ihrer Person",
                "customerchangednotification",
                customerChanged
        ));
    }


    /**
     *
     */
    public void sendPolicyCreatedMail(PolicyCreatedEvent policyCreated) {
        this.sendEmail(emailFactory.buildEmail(
                policyCreated.getEmail(),
                sender,
                "Ihre MeowMed Vertragsinformationen",
                "policynotification",
                policyCreated
        ));
    }

    public void sendPolicyChangedMail(PolicyChangedEvent policyChangedEvent) {
        this.sendEmail(emailFactory.buildEmail(
                policyChangedEvent.getEmail(),
                sender,
                "Ihre Vertragsänderungen",
                "policychangednotification",
                policyChangedEvent
        ));
    }

    public void sendPolicyCancelledMail(PolicyChangedEvent policyChangedEvent) {
        this.sendEmail(emailFactory.buildEmail(
                policyChangedEvent.getEmail(),
                sender,
                "Kündigung Ihres Vertrages",
                "policycancellednotification",
                policyChangedEvent
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