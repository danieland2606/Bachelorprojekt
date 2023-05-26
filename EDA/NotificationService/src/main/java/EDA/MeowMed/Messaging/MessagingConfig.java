package EDA.MeowMed.Messaging;

import com.rabbitmq.client.AMQP;
import event.Keys;
import event.Topics;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Class for configuration of RabbitMQ messaging
 */
@Configuration
public class MessagingConfig {

    @Bean
    public TopicExchange topic() {
        return new TopicExchange(Topics.TOPIC_EXCHANGE_NAME);
    }

    private static class ReceiverConfig {

        /**
         * Creates a new queue with the given name for receiving events
         * Queue will be deleted after shutdown of broker
         *
         * @return New Queue object with specified name
         */
        @Bean
        public Queue customerCreatedQueue() {
            return new Queue("customercreated");
        }

        @Bean
        public Queue customerChangedQueue() {
            return new Queue("customerchanged");
        }

        @Bean
        public Queue customerCancelledQueue() {
            return new Queue("customercancelled");
        }

        @Bean
        public Queue policyCreatedQueue() {
            return new Queue("policycreated");
        }

        @Bean
        public Queue policyChangedQueue() {
            return new Queue("policychanged");
        }

        @Bean
        public Queue policyCancelledQueue() {
            return new Queue("policycancelled");
        }

        /**
         * Binds a queue to an exchange
         * Consumes only events witch specified routing key
         *
         * @param topic                Exchange to bind to
         * @param customerCreatedQueue Queue used by consumer
         * @return Topic Exchange object
         */
        @Bean
        public Binding customerCreatedBinding(TopicExchange topic, Queue customerCreatedQueue) {
            return BindingBuilder.bind(customerCreatedQueue()).to(topic).with(Keys.CUSTOMER_CREATED_KEY);
        }

        @Bean
        public Binding customerChangedBinding(TopicExchange topic, Queue customerChangedQueue) {
            return BindingBuilder.bind(customerChangedQueue()).to(topic).with(Keys.CUSTOMER_CHANGED_KEY);
        }

        @Bean
        public Binding cutomerCancelledBinding(TopicExchange topic, Queue customerChangedQueue) {
            return BindingBuilder.bind(customerCancelledQueue()).to(topic).with(Keys.CUSTOMER_CHANGED_CANCELLED_KEY);
        }

        @Bean
        public Binding policyCreatedBinding(TopicExchange topic, Queue policyCreatedQueue) {
            return BindingBuilder.bind(policyCreatedQueue()).to(topic).with(Keys.POLICY_CREATED_KEY);
        }

        @Bean
        public Binding policyChangedBinding(TopicExchange topic, Queue policyChangedQueue) {
            return BindingBuilder.bind(policyChangedQueue()).to(topic).with(Keys.POLICY_CHANGED_KEY);
        }

        @Bean
        public Binding policyCancelledBinding(TopicExchange topic, Queue policyChangedQueue) {
            return BindingBuilder.bind(policyCancelledQueue()).to(topic).with(Keys.POLICY_CHANGED_CANCELLED_KEY);
        }

        @Bean
        public EventReceiver receiver() {
            return new EventReceiver();
        }

    }
}
