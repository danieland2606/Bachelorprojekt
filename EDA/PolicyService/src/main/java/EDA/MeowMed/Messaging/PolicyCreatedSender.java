package EDA.MeowMed.Messaging;

import EDA.MeowMed.Messaging.EventObjects.PolicyCreatedEvent;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class PolicyCreatedSender {
    @Autowired
    private RabbitTemplate template;

    @Autowired
    @Qualifier("PolicyTopic")
    private TopicExchange topic;

    public void send(PolicyCreatedEvent policyCreatedEvent) {
        this.template.convertAndSend(topic.getName(), "policy.created", policyCreatedEvent);
        System.out.println("Sent newly created Policy!");
    }
}