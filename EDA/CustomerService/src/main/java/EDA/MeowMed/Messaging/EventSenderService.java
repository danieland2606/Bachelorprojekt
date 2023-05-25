package EDA.MeowMed.Messaging;


import EDA.MeowMed.Application.CustomerValidationService;
import event.objects.customer.CustomerChangedEvent;
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

    private final String created_routing_key = "customer.created";
    private final String changed_routing_key = "customer.changed";
    private final String cancelled_routing_key = "cancelled.customer.changed";


    /**
     * Sends an event to a topic exchange when new customer is created
     * Topic name is defined in 'MessagingConfig' class
     *
     * @param customer Data of newly crated customer
     * @return Status code for sending success
     */
    public boolean sendCustomerCreatedEvent(Customer customer) {
        try {
            template.convertAndSend(topicExchange.getName(), created_routing_key, customer.createCustomerCreatedEvent());
            System.out.println(" [x] Sent");
        } catch (Exception e) {
            System.out.println("Fehler beim Senden");
            e.printStackTrace();
        }
        return true;
    }

    public boolean sendCustomerChangedEvent(Customer customer) {
        try {
            CustomerChangedEvent customerChangedEvent = customer.createCustomerChangedEvent();
            String routing_key;
            if (customer.getEmploymentStatus().equals(CustomerValidationService.cancelStateEmploymentStatus)) {
                routing_key = cancelled_routing_key;
            } else {
                routing_key = changed_routing_key;
            }

            template.convertAndSend(topicExchange.getName(), routing_key,customerChangedEvent);
            System.out.println(" [x] Sent");
        } catch (Exception e) {
            System.out.println("Fehler beim Senden");
            e.printStackTrace();
        }
        return true;
    }
}
