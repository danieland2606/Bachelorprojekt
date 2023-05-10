package EDA.MeowMed.Messaging;

import events.customer.CustomerChangedEvent;
import events.customer.CustomerCreatedEvent;
import EDA.MeowMed.Logic.PolicyService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;


public class CustomerReceiver {

    private final PolicyService policyService;

    @Autowired
    public CustomerReceiver(PolicyService policyService) {
        this.policyService = policyService;
    }

    @RabbitListener(queues = "#{CustomerCreatedQueue.name}")
    public void receiveCreated(CustomerCreatedEvent customerCreatedEvent) {
        System.out.println("Newly created Customer received!");
        this.policyService.addNewCustomer(customerCreatedEvent);
    }

    @RabbitListener(queues = "#{CustomerChangedQueue.name}")
    public void receiveChanged(CustomerChangedEvent customerChangedEvent) {
        System.out.println("Newly created Customer received!");
        this.policyService.updateCustomer(customerChangedEvent);
    }
}