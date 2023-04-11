package EDA.MeowMed.Messaging;


import EDA.MeowMed.Messaging.EventObjects.CustomerCreatedEvent;
import EDA.MeowMed.Persistence.Entity.Customer;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventSenderService {

    @Autowired
    private RabbitTemplate template;
    @Autowired
    private TopicExchange topicExchange;

    public EventSenderService() {
    }

    private final String routingKey = "customer.created";

    private void sendEvent(String message) {
        template.convertAndSend(topicExchange.getName(), routingKey, message);
        System.out.println(" [x] Sent '" + message + "'");
    }

    /**
     * Sends an event to a topic exchange when new customer is created
     * Topic name is defined in 'MessagingConfig' class
     * @param customer Data of newly crated customer
     * @return Status code for sending success
     */
    public boolean sendCustomerCreatedEvent(Customer customer) {
        try {
            template.convertAndSend(topicExchange.getName(), routingKey, new CustomerCreatedEvent(customer));
            System.out.println(" [x] Sent");
        } catch (Exception e) {
            System.out.println("Fehler beim Senden");
            e.printStackTrace();
        }
        return true;
    }
}
