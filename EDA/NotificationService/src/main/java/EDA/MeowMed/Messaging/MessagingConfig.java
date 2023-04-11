package EDA.MeowMed.Messaging;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Class for configuration of RabbitMQ messaging
 */
@Configuration
public class MessagingConfig {

    private final String exchangeName = "exchange";

    /**
     * Creates a new RabbitMQ exchange named after the 'exchangeName' variable
     * @return a new TopicExchange object with name of 'exchangeName' variable
     */
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(exchangeName);
    }

//    @Bean
//    public DirectExchange directPolicy() {
//        return new DirectExchange("policy.direct");
//    }


    private static class ReceiverConfig {

        /**
         * Creates a new AnonymousQueue with autogenerated name for receiving events
         * Queue will be deleted after shutdown of broker
         * @return New AnonymousQueue object
         */
        @Bean
        public Queue autoDeleteQueue1() {
            return new AnonymousQueue();
        }

        @Bean
        public Queue autoDeleteQueue2() {
            return new AnonymousQueue();
        }

        /**
         * Binds a queue to an exchange
         * Consumes only events witch specified routing key
         * @param topic Exchange to bind to
         * @param autoDeleteQueue1 Queue used by consumer
         * @return Topic Exchange object
         */
        @Bean
        public Binding customerBinding(TopicExchange topic, Queue autoDeleteQueue1) {
            return BindingBuilder.bind(autoDeleteQueue1).to(topic).with("customer.created");
        }

        @Bean
        public Binding policyBinding(DirectExchange direct, Queue autoDeleteQueue2) {
            return BindingBuilder.bind(autoDeleteQueue2).to(direct).with("policy.created");
        }

        @Bean
        public EventReceiver receiver() {
            return new EventReceiver();
        }

    }
}
