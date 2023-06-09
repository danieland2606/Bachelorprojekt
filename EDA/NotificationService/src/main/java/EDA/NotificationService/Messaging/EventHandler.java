package EDA.NotificationService.Messaging;

import EDA.NotificationService.Application.DatabaseService;
import EDA.NotificationService.Application.EmailService;
import EDA.NotificationService.Persistence.Entity.Customer;
import EDA.NotificationService.Persistence.Entity.Policy;
import event.objects.billing.BillingEvent;
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
    /**
     * Handles a customer created event by adding the customer to the database and sending a customer created email.
     *
     * @param customerEvent The event representing the creation of a customer.
     */
    public void handleCustomerCreated(CustomerEvent customerEvent) {
        Customer customer = databaseService.addCustomer(customerEvent);
        emailService.sendCustomerCreatedMail(customer);
    }
    /**
     * Handles a customer changed event by updating the customer in the database and sending a customer changed email.
     *
     * @param customerEvent The event representing the changes made to a customer.
     */
    public void handleCustomerChanged(CustomerEvent customerEvent) {
        Customer customer = databaseService.replaceCustomer(customerEvent);
        emailService.sendCustomerChangedMail(customer);
    }
    /**
     * Handles a policy created event by adding the policy to the database.
     *
     * @param policyEvent The event representing the creation of a policy.
     */
    public void handlePolicyCreated(PolicyEvent policyEvent) {
        databaseService.addPolicy(policyEvent);
    }
    /**
     * Handles a policy changed event by updating the policy in the database.
     *
     * @param policyEvent The event representing the changes made to a policy.
     */
    public void handlePolicyChanged(PolicyEvent policyEvent) {
        databaseService.replacePolicy(policyEvent);
    }
    /**
     * Handles a policy cancelled event by updating the policy in the database and sending a policy cancelled email.
     *
     * @param policyEvent The event representing the cancellation of a policy.
     */
    public void handlePolicyCancelled(PolicyEvent policyEvent) {
        Policy policy = databaseService.replacePolicy(policyEvent);
        emailService.sendPolicyCancelledMail(policy);
    }
    /**
     * Handles a policy created billing event by retrieving the policy from the database and sending a policy created email.
     *
     * @param billingEvent The event representing the billing information for a newly created policy.
     */
    public void handlePolicyCreatedBilling(BillingEvent billingEvent){
        Policy policy = databaseService.getPolicy(billingEvent.getPid());
        emailService.sendPolicyCreatedMail(policy);
    }
    /**
     * Handles a policy changed billing event by retrieving the policy from the database and sending a policy changed email.
     *
     * @param billingEvent The event representing the billing information for a changed policy.
     */
    public void handlePolicyChangedBilling(BillingEvent billingEvent){
        Policy policy = databaseService.getPolicy(billingEvent.getPid());
        emailService.sendPolicyChangedMail(policy);
    }

}
