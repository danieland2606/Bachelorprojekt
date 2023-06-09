package EDA.MeowMed.Messaging;

import EDA.MeowMed.Exceptions.ObjectNotFoundException;
import EDA.MeowMed.Logic.PolicyService;
import event.objects.customer.CustomerEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;


public class CustomerReceiver {

    private final PolicyService policyService;

    @Autowired
    public CustomerReceiver(PolicyService policyService) {
        this.policyService = policyService;
    }

    @RabbitListener(queues = "#{CustomerCreatedQueue.name}")
    public void receiveCreated(CustomerEvent customerEvent) {
        System.out.println("Newly created Customer received!");
        this.policyService.addNewCustomer(customerEvent);
    }

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
