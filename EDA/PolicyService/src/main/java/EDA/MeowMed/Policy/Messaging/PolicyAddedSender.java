package EDA.MeowMed.Policy.Messaging;

import EDA.MeowMed.Policy.Persistence.Entity.Policy;
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

    public void send(Policy policy) {
        this.template.convertAndSend(topic.getName(), "policy", policy);
        System.out.println("Sent newly created Policy!");
    }
}
