package EDA.PolicyService.Messaging;

import event.Keys;
import event.objects.policy.PolicyEvent;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class PolicySender {
    @Autowired
    private RabbitTemplate template;

    @Autowired
    private TopicExchange topic;

    public void sendPolicyCreated(PolicyEvent policyEvent) {
        this.template.convertAndSend(topic.getName(), Keys.POLICY_CREATED_KEY, policyEvent);
        System.out.println("Sent newly created Policy!");
    }

    public void sendPolicyChanged(PolicyEvent policyEvent) {
        this.template.convertAndSend(topic.getName(), Keys.POLICY_CHANGED_KEY, policyEvent);
        System.out.println("Sent Policy Changed Event");
    }

    public void sendPolicyCancelled(PolicyEvent policyEvent) {
        this.template.convertAndSend(topic.getName(), Keys.POLICY_CHANGED_CANCELLED_KEY, policyEvent);
        System.out.println("Sent Policy Cancelled Event");
    }
}
