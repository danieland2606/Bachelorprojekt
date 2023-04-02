package EDA.MeowMed.Messaging;


import EDA.MeowMed.Persistence.Entity.Customer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class EventSender {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private DirectExchange direct;

    private final String key = "customer_created";

    private void sendEvent(String message) {
        template.convertAndSend(direct.getName(), key, message);
        System.out.println(" [x] Sent '" + message + "'");
    }

    public boolean sendNewCustomerEvent(Customer customer) {
        String customerJson = null;
        try {
            customerJson = new ObjectMapper().writeValueAsString(customer);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        sendEvent(customerJson);
        return true;
    }
}
