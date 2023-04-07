package EDA.MeowMed.Messaging;


import EDA.MeowMed.Messaging.EventObjects.CustomerCreatedEvent;
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

    public EventSenderService() {
    }

    private final String key = "customer_created";

    private void sendEvent(String message) {
        template.convertAndSend(direct.getName(), key, message);
        System.out.println(" [x] Sent '" + message + "'");
    }

    public boolean sendCustomerCreatedEvent(Customer customer) {
        try {
            template.convertAndSend(direct.getName(), key, new CustomerCreatedEvent(customer));
            System.out.println(" [x] Sent");
        } catch (Exception e) {
            System.out.println("Fehler beim Senden");
            e.printStackTrace();
        }
        return true;
    }
}
