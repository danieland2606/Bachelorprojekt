package EDA.BillingService.Messaging;

import EDA.BillingService.Logic.BillingService;
import event.Keys;
import event.Topics;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingConfig {

    @Bean
    public TopicExchange topic() {
        return new TopicExchange(Topics.TOPIC_EXCHANGE_NAME);
    }

    @Bean
    public Receiver receiver(BillingService billingService) {
        return new Receiver(billingService);
    }

    @Bean(name = "CustomerCreatedQueue")
    public Queue CustomerCreatedQueue() {
        return new Queue("customercreated");
    }

    @Bean(name = "PolicyCreatedQueue")
    public Queue PolicyCreatedQueue() {
        return new Queue("policycreated");
    }

    @Bean(name = "PolicyChangedQueue")
    public Queue PolicyChangedQueue() {
        return new Queue("policychanged");
    }

    @Bean
    public Binding bindCustomerCreated(TopicExchange topic, @Qualifier("CustomerCreatedQueue") Queue queue) {
        return BindingBuilder.bind(queue).to(topic).with(Keys.CUSTOMER_CREATED_KEY);
    }

    @Bean
    public Binding bindPolicyCreated(TopicExchange topic, @Qualifier("PolicyCreatedQueue") Queue queue) {
        return BindingBuilder.bind(queue).to(topic).with(Keys.POLICY_CREATED_KEY);
    }

    @Bean
    public Binding binPolicyChanged(TopicExchange topic, @Qualifier("PolicyChangedQueue") Queue queue) {
        return BindingBuilder.bind(queue).to(topic).with(Keys.POLICY_CHANGED_ANY_KEY);
    }
}
