package EDA.NotificationService.Messaging;

import event.Keys;
import event.Topics;
import org.springframework.amqp.core.*;
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
            return new Queue("notification_customercreated");
        }

        @Bean
        public Queue customerChangedQueue() {
            return new Queue("notification_customerchanged");
        }

        @Bean
        public Queue policyCreatedQueue() {
            return new Queue("notification_policycreated");
        }

        @Bean
        public Queue policyChangedQueue() {
            return new Queue("notification_policychanged");
        }

        @Bean
        public Queue policyCancelledQueue() {
            return new Queue("notification_policycancelled");
        }

        @Bean
        public Queue policyCreatedBillingQueue() {
            return new Queue("notification_policycreatedbilling");
        }

        @Bean
        public Queue policyChangedBillingQueue() {
            return new Queue("notification_policychangedbilling");
        }



        /**
         * Binds a queue to an exchange
         * Consumes only events witch specified routing key
         *
         * @param topic Exchange to bind to
         * @param queue Queue used by consumer
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
        public Binding policyCreatedBillingBinding(TopicExchange topic, Queue policyCreatedBillingQueue) {
            return BindingBuilder.bind(policyCreatedBillingQueue()).to(topic).with(Keys.POLICY_CREATED_BILL_KEY);
        }

        @Bean
        public Binding policyChangedBillingBinding(TopicExchange topic, Queue policyChangedBillingQueue) {
            return BindingBuilder.bind(policyChangedBillingQueue()).to(topic).with(Keys.POLICY_CHANGED_BILL_KEY);
        }

        @Bean
        public EventReceiver receiver() {
            return new EventReceiver();
        }

    }
}
