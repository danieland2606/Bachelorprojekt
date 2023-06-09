package EDA.NotificationService.Messaging;

import event.objects.billing.BillingEvent;
import event.objects.customer.CustomerEvent;
import event.objects.policy.PolicyEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Class for receiving AMQP events via RabbitMQ
 */
public class EventReceiver {

    @Autowired
    private EventHandler eventHandler;

    @RabbitListener(queues = "#{customerCreatedQueue.name}")
    public void receiveCustomer(CustomerEvent customerEvent) throws InterruptedException {
        System.out.println("Received newly created Customer");
        eventHandler.handleCustomerCreated(customerEvent);
    }

    @RabbitListener(queues = "#{customerChangedQueue.name}")
    public void receiveCustomerChanged(CustomerEvent customerEvent) throws InterruptedException {
        System.out.println("Received changed Customer Data");
        eventHandler.handleCustomerChanged(customerEvent);
    }

    @RabbitListener(queues = "#{policyCreatedQueue.name}")
    public void receivePolicy(PolicyEvent policyEvent) throws InterruptedException {
        System.out.println("Received newly created Policy");
        eventHandler.handlePolicyCreated(policyEvent);
    }

    @RabbitListener(queues = "#{policyChangedQueue.name}")
    public void receivePolicyChanged(PolicyEvent policyEvent) throws InterruptedException {
        System.out.println("Received changed Policy Data");
        eventHandler.handlePolicyChanged(policyEvent);
    }

    @RabbitListener(queues = "#{policyCancelledQueue.name}")
    public void receivePolicyCancelled(PolicyEvent policyEvent) throws InterruptedException {
        System.out.println("Received cancel Policy Event");
        eventHandler.handlePolicyCancelled(policyEvent);
    }

    @RabbitListener(queues = "#{policyCreatedBillingQueue.name}")
    public void receivePolicyCreatedBilling(BillingEvent billingEvent) throws InterruptedException {
        System.out.println("Received created Billing Event");
        eventHandler.handlePolicyCreatedBilling(billingEvent);
    }

    @RabbitListener(queues = "#{policyChangedBillingQueue.name}")
    public void receivePolicyChangedBilling(BillingEvent billingEvent) throws InterruptedException {
        System.out.println("Received changed Billing Event");
        eventHandler.handlePolicyChangedBilling(billingEvent);
    }
}
