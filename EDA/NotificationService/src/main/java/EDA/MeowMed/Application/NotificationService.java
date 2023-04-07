package EDA.MeowMed.Application;

import EDA.MeowMed.Messaging.EventObjects.CustomerCreatedEvent;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private JavaMailSender mailSender;
    private final String ownMail= "noreply@meowmed.paw";
    private final String subject = "Willkommen bei Meowmed!";

    public void sendCustomerCreatedMail(CustomerCreatedEvent newCustomer) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(ownMail);
        message.setTo("receiver.meowmed@gmail.com");
        message.setSubject(subject);
        message.setText("test");
        mailSender.send(message);
    }

//    public void sendPolicyCreatedMail(NewPolicyEvent newPolicy) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("noreply@meowmed.com");
//        message.setTo(to);
//        message.setSubject(subject);
//        message.setText(text);
//        mailSender.send(message);
//    }

}