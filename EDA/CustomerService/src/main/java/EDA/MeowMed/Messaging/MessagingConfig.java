package EDA.MeowMed.Messaging;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile({"CustomerService","routing"})
@Configuration
public class MessagingConfig {

    @Bean
    public DirectExchange direct() {
        return new DirectExchange("customer-sender");
    }


    @Bean
    public EventSender sender() {
        return new EventSender();
    }
}
