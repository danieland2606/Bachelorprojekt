package EDA.MeowMed.Messaging;

import EDA.MeowMed.Application.NotificationService;
import EDA.MeowMed.Messaging.EventObjects.NewCustomerEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;

public class EventReceiver {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private DirectExchange direct;

    @Autowired
    private NotificationService notificationService;

    @RabbitListener(queues = "#{autoDeleteQueue1.name}")
    public void receiveCustomer(NewCustomerEvent in) throws InterruptedException {
        notificationService.sendNewCustomerMail(in);
    }

    @RabbitListener(queues = "#{autoDeleteQueue2.name}")
    public void receivePolicy(String in) throws InterruptedException {
        receive(in, 2);
    }

    public void receive(String in, int receiver) throws InterruptedException {

    }
}
