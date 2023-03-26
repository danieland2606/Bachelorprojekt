package EDA.MeowMed.Policy.Messaging;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingConfig {

    @Bean
    public PolicyAddedSender sender() {
        return new PolicyAddedSender();
    }

    @Bean(name = "PolicyAddedTopic")
    public TopicExchange policyAddedTopic() {
        return new TopicExchange("policy");
    }

    @Bean
    public CustomerCreatedReceiver receiver() {
        return new CustomerCreatedReceiver();
    }

    @Bean(name = "CustomerCreatedTopic")
    public TopicExchange customerCreatedTopic() {
        return new TopicExchange("customer");
    }

    @Bean(name = "CustomerCreatedQueue")
    public Queue CustomerCreatedQueue() {
        return new AnonymousQueue();
    }

    @Bean
    public Binding bindToCustomerCreatedTopic(@Qualifier("CustomerCreatedTopic") TopicExchange topic, @Qualifier("CustomerCreatedQueue") Queue queue) {
        return BindingBuilder.bind(queue).to(topic).with("customer");
    }
}
