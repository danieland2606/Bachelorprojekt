package EDA.MeowMed.Application;

import EDA.MeowMed.Email.Email;
import EDA.MeowMed.Email.EmailFactory;
import event.objects.customer.CustomerEvent;
import event.objects.policy.PolicyEvent;
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
    public void sendCustomerCreatedMail(CustomerEvent customerEvent) {
        this.sendEmail(emailFactory.buildEmail(
                customerEvent.getEmail(),
                sender,
                "Willkommen bei MeowMed!",
                "customernotification",
                customerEvent
        ));
    }

    public void sendCustomerChangedMail(CustomerEvent customerEvent) {
        this.sendEmail(emailFactory.buildEmail(
                customerEvent.getEmail(),
                sender,
                "Änderung der Angaben zu Ihrer Person",
                "customerchangednotification",
                customerEvent
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