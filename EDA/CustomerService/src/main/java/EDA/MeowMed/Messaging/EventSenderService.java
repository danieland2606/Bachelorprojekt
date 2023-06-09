package EDA.MeowMed.Messaging;


import event.Keys;
import event.objects.customer.CustomerEvent;
import EDA.MeowMed.Persistence.Entity.Customer;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class for publishing events to specific exchanges
 */
@Service
public class EventSenderService {

    @Autowired
    private RabbitTemplate template;
    @Autowired
    private TopicExchange topicExchange;

    public EventSenderService() {
    }

    /**
     * Sends an event to a topic exchange when new customer is created
     * Topic name is defined in 'MessagingConfig' class
     *
     * @param customer Data of newly crated customer
     * @return Status code for sending success
     */
    public boolean sendCustomerCreatedEvent(Customer customer) {
        try {
            template.convertAndSend(topicExchange.getName(), Keys.CUSTOMER_CREATED_KEY, customer.toEvent());
            System.out.println(" [x] Sent");
        } catch (Exception e) {
            System.out.println("Fehler beim Senden");
            e.printStackTrace();
        }
        return true;
    }

    public boolean sendCustomerChangedEvent(Customer customer) {
        try {
            template.convertAndSend(topicExchange.getName(), Keys.CUSTOMER_CHANGED_KEY,customer.toEvent());
            System.out.println(" [x] Sent");
        } catch (Exception e) {
            System.out.println("Fehler beim Senden");
            e.printStackTrace();
        }
        return true;
    }
}
