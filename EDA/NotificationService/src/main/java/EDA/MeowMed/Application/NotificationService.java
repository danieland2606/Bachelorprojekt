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
    public void sendCustomerCancelledMail(CustomerChangedEvent customerChanged) {
        this.sendEmail(emailFactory.buildEmail(
                customerChanged.getEmail(),
                sender,
                "Kündigung Ihres Vertrags bei MeowMed",
                "customercancellednotification",
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
                "policychangednotification",
                "Ihre Vertragsänderungen",
                policyChangedEvent
        ));
    }

    public void sendPolicyCancelledMail(PolicyChangedEvent policyChangedEvent) {
        this.sendEmail(emailFactory.buildEmail(
                policyChangedEvent.getEmail(),
                sender,
                "policycancellednotification",
                "Kündigung Ihres Vertrages",
                policyChangedEvent
        ));
    }

    private void sendEmail(Email email) {
        try {
            emailSenderService.sendHtmlMessage(email);
        } catch (MessagingException e) { //todo: This needs to change
            System.out.println("Fehler beim Senden der " + email.getTemplate() + " Mail, für" + email.getTo());
            e.printStackTrace();
        }
        System.out.println(email.getTemplate() + " Mail gesendet!");
    }
}