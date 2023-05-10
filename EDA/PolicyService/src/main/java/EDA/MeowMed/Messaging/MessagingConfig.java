package EDA.MeowMed.Messaging;

import EDA.MeowMed.Logic.PolicyService;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingConfig {

    @Bean
    public PolicySender sender() {
        return new PolicySender();
    }

    @Bean
    public TopicExchange topic() {
        return new TopicExchange("MeowMedTopicExchange");
    }

    @Bean
    public CustomerReceiver receiver(PolicyService policyService) {
        return new CustomerReceiver(policyService);
    }

    @Bean(name = "CustomerCreatedQueue")
    public Queue CustomerCreatedQueue() {
        return new AnonymousQueue();
    }

    @Bean(name = "CustomerChangedQueue")
    public Queue CustomerChangedQueue() {
        return new AnonymousQueue();
    }

    @Bean
    public Binding bindCustomerCreated(TopicExchange topic, @Qualifier("CustomerCreatedQueue") Queue queue) {
        return BindingBuilder.bind(queue).to(topic).with("customer.created");
    }

    @Bean
    public Binding binCustomerChanged(TopicExchange topic, @Qualifier("CustomerChangedQueue") Queue queue) {
        return BindingBuilder.bind(queue).to(topic).with("customer.changed");
    }
}
