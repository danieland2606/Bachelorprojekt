package EDA.MeowMed.Messaging;

import EDA.MeowMed.Messaging.EventObjects.CustomerCreatedEvent;
import EDA.MeowMed.Logic.PolicyService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;


public class CustomerCreatedReceiver {

    private final PolicyService policyService;

    @Autowired
    public CustomerCreatedReceiver(PolicyService policyService) {
        this.policyService = policyService;
    }

    @RabbitListener(queues = "#{CustomerCreatedQueue.name}")
    public void receive(CustomerCreatedEvent customerCreatedEvent) {
        System.out.println("Newly created Customer received!");
        this.policyService.addNewCustomer(customerCreatedEvent);
    }
}
