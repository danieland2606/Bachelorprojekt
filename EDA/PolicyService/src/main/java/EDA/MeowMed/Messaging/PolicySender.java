package EDA.MeowMed.Messaging;

import events.policy.PolicyCreatedEvent;
import events.policy.PolicyChangedEvent;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class PolicySender {
    @Autowired
    private RabbitTemplate template;

    @Autowired
    @Qualifier("PolicyTopic")
    private TopicExchange topic;

    public void sendPolicyCreated(PolicyCreatedEvent policyCreatedEvent) {
        this.template.convertAndSend(topic.getName(), "policy.created", policyCreatedEvent);
        System.out.println("Sent newly created Policy!");
    }

    public void sendPolicyChanged(PolicyChangedEvent policyChangedEvent) {
        this.template.convertAndSend(topic.getName(), "policy.changed", policyChangedEvent);
        System.out.println("Sent Policy Changed Event");
    }
}
