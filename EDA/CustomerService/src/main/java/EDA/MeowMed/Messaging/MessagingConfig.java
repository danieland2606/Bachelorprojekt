package EDA.MeowMed.Messaging;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingConfig {

    @Bean
    public DirectExchange direct() {
        return new DirectExchange("customer-sender");
    }

    @Bean
    public EventSenderService sender() {
        return new EventSenderService();
    }
}
