package EDA.MeowMed.Messaging;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

    @Bean
    public EventSenderService sender() {
        return new EventSenderService();
    }
}
