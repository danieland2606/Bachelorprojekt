package EDA.MeowMed.Messaging;

import EDA.MeowMed.Logic.BillingService;
import events.customer.CustomerCreatedEvent;
import events.policy.PolicyChangedEvent;
import events.policy.PolicyCreatedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

public class Receiver {

    private final BillingService billingService;

    @Autowired
    public Receiver(BillingService billingService) {
        this.billingService = billingService;
    }

    @RabbitListener(queues = "#{CustomerCreatedQueue.name}")
    public void receiveCustomerCreated(CustomerCreatedEvent customerCreatedEvent) {
        System.out.println("Newly created Customer received!");
        this.billingService.addCustomer(customerCreatedEvent);
    }

    @RabbitListener(queues = "#{PolicyCreatedQueue.name}")
    public void receivePolicyCreated(PolicyCreatedEvent policyCreatedEvent) {
        System.out.println("Newly created Policy received!");
        this.billingService.addBillForNewlyCreatedPolicy(policyCreatedEvent.getPolicy());
    }

    @RabbitListener(queues = "#{PolicyChangedQueue.name}")
    public void receivePolicyChanged(PolicyChangedEvent policyChangedEvent) {
        System.out.println("Policy Change received!");
        this.billingService.addBillForUpdatedPolicy(policyChangedEvent.getPolicy());
    }
}
