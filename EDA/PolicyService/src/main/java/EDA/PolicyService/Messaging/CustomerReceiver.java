package EDA.PolicyService.Messaging;

import EDA.PolicyService.Exceptions.ObjectNotFoundException;
import EDA.PolicyService.Logic.PolicyService;
import event.objects.customer.CustomerEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Receiver class to receive Customer related events over RabbitMQ
 */
public class CustomerReceiver {

    private final PolicyService policyService;

    @Autowired
    public CustomerReceiver(PolicyService policyService) {
        this.policyService = policyService;
    }

    /**
     * Receives customerCreatedEvents and triggers the PolicyService to add the respective customer
     * @param customerEvent The customerCreatedEvent
     */
    @RabbitListener(queues = "#{CustomerCreatedQueue.name}")
    public void receiveCreated(CustomerEvent customerEvent) {
        System.out.println("Newly created Customer received!");
        this.policyService.addNewCustomer(customerEvent);
    }

    /**
     * Receives customerChangedEvents and triggers the PolicyService to update the data of the respective customer
     * @param customerEvent
     */
    @RabbitListener(queues = "#{CustomerChangedQueue.name}")
    public void receiveChanged(CustomerEvent customerEvent) {
        System.out.println("Changed Customer received!");
        try {
            this.policyService.updateCustomer(customerEvent);
        } catch ( ObjectNotFoundException e) {
            System.out.println("Kunde mit der id " + customerEvent.getCid()
                    + " existiert nicht");
            e.printStackTrace();
        }
    }
}
