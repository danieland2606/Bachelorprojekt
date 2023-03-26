package EDA.MeowMed.Policy.Messaging;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class PolicyAddedSender {
    @Autowired
    private RabbitTemplate template;

    @Autowired
    @Qualifier("PolicyAddedTopic")
    private TopicExchange topic;

    public void send() {
        String message = "Policy has been created: Please send mail PLIIIIIIIIIIIZ";
        this.template.convertAndSend(topic.getName(), "policy", message);
        System.out.println(" [x] Sent '" + message + "'");
    }
}
