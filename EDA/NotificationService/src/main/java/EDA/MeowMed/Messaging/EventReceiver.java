package EDA.MeowMed.Messaging;

import EDA.MeowMed.Application.NotificationService;
import event.objects.customer.CustomerChangedEvent;
import event.objects.customer.CustomerCreatedEvent;
import event.objects.policy.PolicyChangedEvent;
import event.objects.policy.PolicyCreatedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Class for receiving AMQP events via RabbitMQ
 */
public class EventReceiver {

    @Autowired
    private NotificationService notificationService;

    @RabbitListener(queues = "#{customerCreatedQueue.name}")
    public void receiveCustomer(CustomerCreatedEvent customerCreatedEvent) throws InterruptedException {
        System.out.println("Received newly created Customer");
        notificationService.sendCustomerCreatedMail(customerCreatedEvent);
    }

    @RabbitListener(queues = "#{customerChangedQueue.name}")
    public void receiveCustomerChanged(CustomerChangedEvent customerChangedEvent) throws InterruptedException {
        System.out.println("Received changed Customer Data");
        notificationService.sendCustomerChangedMail(customerChangedEvent);
    }

    @RabbitListener(queues = "#{policyCreatedQueue.name}")
    public void receivePolicy(PolicyCreatedEvent policyCreatedEvent) throws InterruptedException {
        System.out.println("Received newly created Policy");
        notificationService.sendPolicyCreatedMail(policyCreatedEvent);
    }

    @RabbitListener(queues = "#{policyChangedQueue.name}")
    public void receivePolicyChanged(PolicyChangedEvent policyChangedEvent) throws InterruptedException {
        System.out.println("Received changed Policy Data");
        notificationService.sendPolicyChangedMail(policyChangedEvent);
    }

    @RabbitListener(queues = "#{policyCancelledQueue.name}")
    public void receivePolicyCancelled(PolicyChangedEvent policyChangedEvent) throws InterruptedException {
        System.out.println("Received cancel Policy Event");
        notificationService.sendPolicyCancelledMail(policyChangedEvent);
    }
}
