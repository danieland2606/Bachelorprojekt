package EDA.MeowMed.Policy.Messaging;

import EDA.MeowMed.Policy.Entity.CustomerEntity;
import EDA.MeowMed.Policy.PolicyService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;


public class CustomerCreatedReceiver {

    private final PolicyService policyService;

    @Autowired
    public CustomerCreatedReceiver(PolicyService policyService) {
        this.policyService = policyService;
    }

    @RabbitListener(queues = "#{CustomerCreatedQueue.name}")
    public void receive(CustomerEntity customer) {
        System.out.println("Newly created Customer received!");
        this.policyService.addNewCustomer(customer);
    }
}
