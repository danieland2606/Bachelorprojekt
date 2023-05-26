package EDA.MeowMed.Messaging;

import event.Keys;
import event.objects.policy.PolicyCreatedEvent;
import event.objects.policy.PolicyChangedEvent;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class PolicySender {
    @Autowired
    private RabbitTemplate template;

    @Autowired
    private TopicExchange topic;

    public void sendPolicyCreated(PolicyCreatedEvent policyCreatedEvent) {
        this.template.convertAndSend(topic.getName(), Keys.POLICY_CREATED_KEY, policyCreatedEvent);
        System.out.println("Sent newly created Policy!");
    }

    public void sendPolicyChanged(PolicyChangedEvent policyChangedEvent) {
        this.template.convertAndSend(topic.getName(), Keys.POLICY_CHANGED_KEY, policyChangedEvent);
        System.out.println("Sent Policy Changed Event");
    }

    public void sendPolicyCancelled(PolicyChangedEvent policyChangedEvent) {
        this.template.convertAndSend(topic.getName(), Keys.POLICY_CHANGED_CANCELLED_KEY, policyChangedEvent);
        System.out.println("Sent Policy Cancelled Event");
    }
}
