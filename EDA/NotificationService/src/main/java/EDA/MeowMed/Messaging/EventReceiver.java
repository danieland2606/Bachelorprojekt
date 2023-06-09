package EDA.MeowMed.Messaging;

import EDA.MeowMed.Application.NotificationService;
import event.objects.customer.CustomerEvent;
import event.objects.policy.PolicyEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Class for receiving AMQP events via RabbitMQ
 */
public class EventReceiver {

    @Autowired
    private NotificationService notificationService;

    @RabbitListener(queues = "#{customerCreatedQueue.name}")
    public void receiveCustomer(CustomerEvent customerEvent) throws InterruptedException {
        System.out.println("Received newly created Customer");
        notificationService.sendCustomerCreatedMail(customerEvent);
    }

    @RabbitListener(queues = "#{customerChangedQueue.name}")
    public void receiveCustomerChanged(CustomerEvent customerEvent) throws InterruptedException {
        System.out.println("Received changed Customer Data");
        notificationService.sendCustomerChangedMail(customerEvent);
    }

    @RabbitListener(queues = "#{policyCreatedQueue.name}")
    public void receivePolicy(PolicyEvent policyEvent) throws InterruptedException {
        System.out.println("Received newly created Policy");
        notificationService.sendPolicyCreatedMail(policyEvent);
    }

    @RabbitListener(queues = "#{policyChangedQueue.name}")
    public void receivePolicyChanged(PolicyEvent policyEvent) throws InterruptedException {
        System.out.println("Received changed Policy Data");
        notificationService.sendPolicyChangedMail(policyEvent);
    }

    @RabbitListener(queues = "#{policyCancelledQueue.name}")
    public void receivePolicyCancelled(PolicyEvent policyEvent) throws InterruptedException {
        System.out.println("Received cancel Policy Event");
        notificationService.sendPolicyCancelledMail(policyEvent);
    }
}
