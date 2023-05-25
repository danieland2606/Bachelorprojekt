package EDA.MeowMed.Messaging;

import EDA.MeowMed.Logic.BillingService;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingConfig {

    @Bean
    public TopicExchange topic() {
        return new TopicExchange("MeowMedTopicExchange");
    }

    @Bean
    public Receiver receiver(BillingService billingService) {
        return new Receiver(billingService);
    }

    @Bean(name = "CustomerCreatedQueue")
    public Queue CustomerCreatedQueue() {
        return new AnonymousQueue();
    }

    @Bean(name = "PolicyCreatedQueue")
    public Queue PolicyCreatedQueue() {
        return new AnonymousQueue();
    }

    @Bean(name = "PolicyChangedQueue")
    public Queue PolicyChangedQueue() {
        return new AnonymousQueue();
    }

    @Bean
    public Binding bindCustomerCreated(TopicExchange topic, @Qualifier("CustomerCreatedQueue") Queue queue) {
        return BindingBuilder.bind(queue).to(topic).with("customer.created");
    }

    @Bean
    public Binding bindPolicyCreated(TopicExchange topic, @Qualifier("PolicyCreatedQueue") Queue queue) {
        return BindingBuilder.bind(queue).to(topic).with("policy.created");
    }

    @Bean
    public Binding binPolicyChanged(TopicExchange topic, @Qualifier("PolicyChangedQueue") Queue queue) {
        return BindingBuilder.bind(queue).to(topic).with("#.policy.changed");
    }
}
