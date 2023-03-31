package EDA.MeowMed.Messaging;


import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerCreatedSender {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private DirectExchange direct;

    private final String key = "customer_created";

    public void send(String message) {
        template.convertAndSend(direct.getName(), key, message);
        System.out.println(" [x] Sent '" + message + "'");
    }
}
