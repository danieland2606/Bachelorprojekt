package EDA.BillingService.Messaging;

import event.Keys;
import event.objects.billing.BillingEvent;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Sender class to send policy related Events over RabbitMQ
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
     * Sends an event to a topic exchange when new policy is created
     * Topic name is defined in 'MessagingConfig' class
     *
     * @param pid ID of Policy
     * @return Status code for sending success
     */
    public boolean sendPolicyCreatedBill(Long pid) {
        try {
            template.convertAndSend(topicExchange.getName(), Keys.POLICY_CREATED_BILL_KEY, new BillingEvent(pid));
            System.out.println(" [x] Sent");
        } catch (Exception e) {
            System.out.println("Fehler beim Senden");
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Sends an event to a topic exchange when policy has been updated
     * Topic name is defined in 'MessagingConfig' class
     *
     * @param pid ID of Policy
     * @return Status code for sending success
     */
    public boolean sendPolicyChangedBill(Long pid) {
        try {
            template.convertAndSend(topicExchange.getName(), Keys.POLICY_CHANGED_BILL_KEY,new BillingEvent(pid));
            System.out.println(" [x] Sent");
        } catch (Exception e) {
            System.out.println("Fehler beim Senden");
            e.printStackTrace();
        }
        return true;
    }
}
