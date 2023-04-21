package EDA.MeowMed.Messaging;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Class for configuration of RabbitMQ messaging
 */
@Configuration
public class MessagingConfig {

    @Bean(name = "CustomerTopic")
    public TopicExchange customerTopic() {
        return new TopicExchange("customer");
    }

    @Bean(name = "PolicyTopic")
    public TopicExchange policyTopic() {
        return new TopicExchange("policy");
    }

    private static class ReceiverConfig {

        /**
         * Creates a new queue with the given name for receiving events
         * Queue will be deleted after shutdown of broker
         * @return New Queue object with specified name
         */
        @Bean
        public Queue customerQueue() { return new Queue("customer");}

        @Bean
        public Queue policyQueue() { return new Queue("policy");}

        /**
         * Binds a queue to an exchange
         * Consumes only events witch specified routing key
         * @param topic Exchange to bind to
         * @param customerQueue Queue used by consumer
         * @return Topic Exchange object
         */
        @Bean
        public Binding customerBinding(@Qualifier("CustomerTopic") TopicExchange topic, Queue customerQueue) {
            return BindingBuilder.bind(customerQueue).to(topic).with("customer.created");

        }

        @Bean
        public Binding policyBinding(@Qualifier("PolicyTopic") TopicExchange topic, Queue policyQueue) {
            return BindingBuilder.bind(policyQueue).to(topic).with("policy.created");
        }

        @Bean
        public EventReceiver receiver() {
            return new EventReceiver();
        }

    }
}
