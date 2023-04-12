package EDA.MeowMed.Messaging;

import EDA.MeowMed.Logic.PolicyService;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingConfig {

    @Bean
    public PolicyCreatedSender sender() {
        return new PolicyCreatedSender();
    }

    @Bean(name = "PolicyTopic")
    public TopicExchange policyAddedTopic() {
        return new TopicExchange("policy");
    }

    @Bean
    public CustomerCreatedReceiver receiver(PolicyService policyService) {
        return new CustomerCreatedReceiver(policyService);
    }

    @Bean(name = "CustomerTopic")
    public TopicExchange customerCreatedTopic() {
        return new TopicExchange("customer");
    }

    @Bean(name = "CustomerCreatedQueue")
    public Queue CustomerCreatedQueue() {
        return new AnonymousQueue();
    }

    @Bean
    public Binding bindToCustomerCreatedTopic(@Qualifier("CustomerTopic") TopicExchange topic, @Qualifier("CustomerCreatedQueue") Queue queue) {
        return BindingBuilder.bind(queue).to(topic).with("customer.created");
    }
}
