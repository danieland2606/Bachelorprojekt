package EDA.MeowMed.Messaging;

import EDA.MeowMed.Application.NotificationService;
import events.customer.CustomerCreatedEvent;
import events.policy.PolicyChangedEvent;
import events.policy.PolicyCreatedEvent;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Class for receiving AMQP events via RabbitMQ
 */
public class EventReceiver {

    @Autowired
    private NotificationService notificationService;

    @RabbitListener(queues = "#{customerQueue.name}")
    public void receiveCustomer(CustomerCreatedEvent customerCreatedEvent) throws InterruptedException {
        System.out.println("Received newly created Customer");
        notificationService.sendCustomerCreatedMail(customerCreatedEvent);
    }

    @RabbitListener(queues = "#{policyCreatedQueue.name}")
    public void receivePolicy(PolicyCreatedEvent policyCreatedEvent) throws InterruptedException {
        System.out.println("Received newly created Policy");
        notificationService.sendPolicyCreatedMail(policyCreatedEvent);
    }

    @RabbitListener(queues = "#{policyChangedQueue.name}")
    public void receivePolicyChanged(PolicyChangedEvent policyChangedEvent) throws InterruptedException {
        System.out.println("Received changed Policy Data");
        notificationService.sendPolicyChangedMail(policyChangedEvent);
    }

    public void receive(String in, int receiver) throws InterruptedException {

    }
}
