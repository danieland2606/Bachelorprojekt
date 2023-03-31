package EDA.MeowMed.Messaging;


import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerCreatedSender {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private DirectExchange exchange;

    private final String[] keys = null;

}
