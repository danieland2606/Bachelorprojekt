package EDA.MeowMed.Messaging;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class MessagingConfig {

    @Bean
    public DirectExchange directCustomer() {
        return new DirectExchange("customer.sender");
    }

//    @Bean
//    public DirectExchange directPolicy() {
//        return new DirectExchange("policy.direct");
//    }

    private static class ReceiverConfig {

        @Bean
        public Queue autoDeleteQueue1() {
            return new AnonymousQueue();
        }

//        @Bean
//        public Queue autoDeleteQueue2() {
//            return new AnonymousQueue();
//        }

        @Bean
        public Binding binding1a(DirectExchange direct, Queue autoDeleteQueue1) {
            return BindingBuilder.bind(autoDeleteQueue1).to(direct).with("customer_created");
        }

//        @Bean
//        public Binding binding2a(DirectExchange direct, Queue autoDeleteQueue2) {
//            return BindingBuilder.bind(autoDeleteQueue2).to(direct).with("created");
//        }

        @Bean
        public EventReceiver receiver() {
            return new EventReceiver();
        }

    }
}
