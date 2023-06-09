package EDA.NotificationService.Messaging;

import EDA.NotificationService.Application.DatabaseService;
import EDA.NotificationService.Application.EmailService;
import EDA.NotificationService.Persistence.Entity.Customer;
import event.objects.customer.CustomerEvent;
import event.objects.policy.PolicyEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventHandler {

    private final EmailService emailService;

    private final DatabaseService databaseService;

    @Autowired
    public EventHandler(EmailService emailService,DatabaseService databaseService){
        this.emailService = emailService;
        this.databaseService=databaseService;
    }

    public void handleCustomerCreated(CustomerEvent customerEvent) {
        Customer customer=databaseService.addCustomer(customerEvent);
        emailService.sendCustomerCreatedMail(customer);
    }

    public void handleCustomerChanged(CustomerEvent customerEvent) {
        Customer customer = databaseService.replaceCustomer(customerEvent);
        emailService.sendCustomerChangedMail(customer);
    }

    public void handlePolicyCreated(PolicyEvent policyEvent) {
        databaseService.addPolicy(policyEvent);
    }

    public void handlePolicyChanged(PolicyEvent policyEvent) {
        databaseService.replacePolicy(policyEvent);
    }

    public void handlePolicyCancelled(PolicyEvent policyEvent) {
        databaseService.replacePolicy(policyEvent);
    }
}
