package EDA.BillingService.Messaging;

import EDA.BillingService.Logic.BillingService;
import event.objects.customer.CustomerEvent;
import event.objects.policy.PolicyEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

public class Receiver {

    private final BillingService billingService;

    @Autowired
    public Receiver(BillingService billingService) {
        this.billingService = billingService;
    }

    @RabbitListener(queues = "#{CustomerCreatedQueue.name}")
    public void receiveCustomerCreated(CustomerEvent customerEvent) {
        System.out.println("Newly created Customer received!");
        this.billingService.addCustomer(customerEvent);
    }

    @RabbitListener(queues = "#{PolicyCreatedQueue.name}")
    public void receivePolicyCreated(PolicyEvent policyEvent) {
        System.out.println("Newly created Policy received!");
        this.billingService.addBillForNewlyCreatedPolicy(policyEvent);
    }

    @RabbitListener(queues = "#{PolicyChangedQueue.name}")
    public void receivePolicyChanged(PolicyEvent policyEvent) {
        System.out.println("Policy Change received!");
        this.billingService.addBillForUpdatedPolicy(policyEvent);
    }
}
