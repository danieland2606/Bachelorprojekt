package EDA.MeowMed.Messaging;


import EDA.MeowMed.Persistence.Entity.Customer;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventSenderService {

    @Autowired
    private RabbitTemplate template;
    @Autowired
    private DirectExchange direct;

    public EventSenderService(){
    }

    private final String key = "customer_created";

    private void sendEvent(String message) {
        template.convertAndSend(direct.getName(), key, message);
        System.out.println(" [x] Sent '" + message + "'");
    }

    public boolean sendNewCustomerEvent(Customer customer) {
        try {

        } catch (Exception e) {
            System.out.println("Fehler beim Senden");
            e.printStackTrace();
        }
        template.convertAndSend(direct.getName(), key, customer);
        System.out.println(" [x] Sent");
        return true;
    }
}
