package EDA.MeowMed.Policy.Messaging;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;


public class CustomerCreatedReceiver {

    @RabbitListener(queues = "#{CustomerCreatedQueue.name}")
    public void receive(String in) {
        System.out.println("Received: '" + in + "' from Topic");
    }
}
