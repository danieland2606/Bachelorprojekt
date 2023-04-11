package EDA.MeowMed.Messaging;

import EDA.MeowMed.Application.NotificationService;
import EDA.MeowMed.Messaging.EventObjects.CustomerCreatedEvent;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Class for receiving AMQP events via RabbitMQ
 */
public class EventReceiver {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private TopicExchange topic;

    @Autowired
    private NotificationService notificationService;

    @RabbitListener(queues = "#{autoDeleteQueue1.name}")
    public void receiveCustomer(CustomerCreatedEvent in) throws InterruptedException {
        System.out.println("Empfangen!");
        notificationService.sendCustomerCreatedMail(in);
    }

    @RabbitListener(queues = "#{autoDeleteQueue2.name}")
    public void receivePolicy(String in) throws InterruptedException {
        receive(in, 2);
    }

    public void receive(String in, int receiver) throws InterruptedException {

    }
}
