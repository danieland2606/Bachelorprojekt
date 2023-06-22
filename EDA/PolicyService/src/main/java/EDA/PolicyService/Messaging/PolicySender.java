package EDA.PolicyService.Messaging;

import event.Keys;
import event.objects.policy.PolicyEvent;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Sender class to send Policy related Events over RabbitMQ
 */
public class PolicySender {
    @Autowired
    private RabbitTemplate template;

    @Autowired
    private TopicExchange topic;

    /**
     * Sends a policyCreated Event
     * @param policyEvent The Event
     */
    public void sendPolicyCreated(PolicyEvent policyEvent) {
        this.template.convertAndSend(topic.getName(), Keys.POLICY_CREATED_KEY, policyEvent);
        System.out.println("Sent newly created Policy!");
    }

    /**
     * Sends a policyChanged Event
     * @param policyEvent The Event
     */
    public void sendPolicyChanged(PolicyEvent policyEvent) {
        this.template.convertAndSend(topic.getName(), Keys.POLICY_CHANGED_KEY, policyEvent);
        System.out.println("Sent Policy Changed Event");
    }

    /**
     * Sends a policyCancelled Event
     * @param policyEvent The Event
     */
    public void sendPolicyCancelled(PolicyEvent policyEvent) {
        this.template.convertAndSend(topic.getName(), Keys.POLICY_CHANGED_CANCELLED_KEY, policyEvent);
        System.out.println("Sent Policy Cancelled Event");
    }
}
