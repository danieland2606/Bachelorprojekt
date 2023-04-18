package EDA.MeowMed.Messaging;

import EDA.MeowMed.Application.NotificationService;
import EDA.MeowMed.Messaging.EventObjects.CustomerCreatedEvent;
import EDA.MeowMed.Messaging.EventObjects.PolicyCreatedEvent;
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

    @RabbitListener(queues = "#{autoDeleteQueue1.name}")
    public void receiveCustomer(CustomerCreatedEvent customerCreatedEvent) throws InterruptedException {
        System.out.println("Empfangen!");
        notificationService.sendCustomerCreatedMail(customerCreatedEvent);
    }

    @RabbitListener(queues = "#{autoDeleteQueue2.name}")
    public void receivePolicy(PolicyCreatedEvent policyCreatedEvent) throws InterruptedException {
        System.out.println("Empfangen!");
        notificationService.sendPolicyCreatedMail(policyCreatedEvent);
    }

    public void receive(String in, int receiver) throws InterruptedException {

    }
}
