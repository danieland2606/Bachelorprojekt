package EDA.BillingService.Messaging;

import EDA.BillingService.Logic.BillingService;
import event.objects.customer.CustomerEvent;
import event.objects.policy.PolicyEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Receiver class to receive customer and policy related events
 */
public class Receiver {

    private final BillingService billingService;

    @Autowired
    public Receiver(BillingService billingService) {
        this.billingService = billingService;
    }

    /**
     * Receives customerCreatedEvents and triggers the BillingService to add the respective customer
     * @param customerEvent The customerCreatedEvent
     */
    @RabbitListener(queues = "#{CustomerCreatedQueue.name}")
    public void receiveCustomerCreated(CustomerEvent customerEvent) {
        System.out.println("Newly created Customer received!");
        this.billingService.addCustomer(customerEvent);
    }

    /**
     * Receives policyCreatedEvents and triggers the BillingService to add a Bill for a newly created Policy
     * @param policyEvent The customerCreatedEvent
     */
    @RabbitListener(queues = "#{PolicyCreatedQueue.name}")
    public void receivePolicyCreated(PolicyEvent policyEvent) {
        System.out.println("Newly created Policy received!");
        this.billingService.addBillForNewlyCreatedPolicy(policyEvent);
    }

    /**
     * Receives policyChangedEvents and triggers the BillingService to add a Bill for an updated policy
     * @param policyEvent The customerCreatedEvent
     */
    @RabbitListener(queues = "#{PolicyChangedQueue.name}")
    public void receivePolicyChanged(PolicyEvent policyEvent) {
        System.out.println("Policy Change received!");
        this.billingService.addBillForUpdatedPolicy(policyEvent);
    }
}
