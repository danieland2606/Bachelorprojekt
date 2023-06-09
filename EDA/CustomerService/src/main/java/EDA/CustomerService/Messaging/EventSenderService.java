package EDA.CustomerService.Messaging;


import EDA.CustomerService.Persistence.Entity.Customer;
import event.Keys;
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
     * Sends a customer created event by converting the customer object to an event and publishing it to the topic exchange.
     *
     * @param customer The customer object representing the created customer.
     */
    public void sendCustomerCreatedEvent(Customer customer) {
        try {
            template.convertAndSend(topicExchange.getName(), Keys.CUSTOMER_CREATED_KEY, customer.toEvent());
            System.out.println(" [x] Sent");
        } catch (Exception e) {
            System.out.println("Fehler beim Senden");
            e.printStackTrace();
        }
    }
    /**
     * Sends a customer changed event by converting the customer object to an event and publishing it to the topic exchange.
     *
     * @param customer The customer object representing the changed customer.
     */
    public void sendCustomerChangedEvent(Customer customer) {
        try {
            template.convertAndSend(topicExchange.getName(), Keys.CUSTOMER_CHANGED_KEY,customer.toEvent());
            System.out.println(" [x] Sent");
        } catch (Exception e) {
            System.out.println("Fehler beim Senden");
            e.printStackTrace();
        }
    }
}
