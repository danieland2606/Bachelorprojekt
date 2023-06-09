package EDA.NotificationService.Application;

import EDA.NotificationService.Persistence.CustomerRepository;
import EDA.NotificationService.Persistence.Entity.Customer;
import EDA.NotificationService.Persistence.Entity.Policy;
import EDA.NotificationService.Persistence.PolicyRepository;
import event.objects.customer.CustomerEvent;
import event.objects.policy.PolicyEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DatabaseService {
    private final CustomerRepository customerRepository;

    private final PolicyRepository policyRepository;

    @Autowired
    public DatabaseService(CustomerRepository customerRepository, PolicyRepository policyRepository){
        this.customerRepository = customerRepository;
        this.policyRepository = policyRepository;
    }

    /**
     * Creates a new customer based on the provided customer event and saves it to the customer repository.
     *
     * @param customerEvent The event containing the information of the customer to be created.
     * @return The created customer.
     */
    public Customer addCustomer(CustomerEvent customerEvent){
        Customer customer = new Customer(customerEvent);
        customerRepository.save(customer);
        return customer;
    }

    /**
     * Replaces an existing customer with updated information based on the provided customer event.
     * The customer ID is preserved from the event and used to update the corresponding customer.
     * The updated customer is then saved to the customer repository.
     *
     * @param customerEvent The event containing the updated information of the customer.
     * @return The replaced customer.
     */
    public Customer replaceCustomer(CustomerEvent customerEvent){
        Customer customer = new Customer(customerEvent);
        customer.setCid(customerEvent.getCid());
        customerRepository.save(customer);
        return customer;
    }

    /**
     * Retrieves a customer based on the provided customer ID.
     *
     * @param cid The ID of the customer to retrieve.
     * @return The retrieved customer.
     * @throws RuntimeException if no customer exists with the provided ID.
     */
    public Customer getCustomer(Long cid){
        Optional<Customer> request = customerRepository.findById(cid);
        if (request.isEmpty()){
            throw new RuntimeException("No such customer exists");
        }
        return request.get();
    }

    /**
     * Creates a new policy based on the provided policy event and
     * associates it with the customer specified by the customer ID from the event.
     * The policy is then saved to the policy repository.
     *
     * @param policyEvent The event containing the information of the policy to be created.
     * @return The created policy.
     */
    public Policy addPolicy(PolicyEvent policyEvent){
        Policy policy = new Policy(policyEvent,getCustomer(policyEvent.getCid()));
        policyRepository.save(policy);
        return policy;
    }

    /**
     * Replaces an existing policy with updated information based on the provided policy event.
     * The customer associated with the policy is retrieved using the customer ID from the event.
     * The policy ID is preserved from the event and used to update the corresponding policy.
     * The updated policy is then saved to the policy repository.
     *
     * @param policyEvent The event containing the updated information of the policy.
     * @return The replaced policy.
     */
    public Policy replacePolicy(PolicyEvent policyEvent){
        Policy policy = new Policy(policyEvent,getCustomer(policyEvent.getCid()));
        policy.setPid(policyEvent.getPid());
        policyRepository.save(policy);
        return policy;
    }

    /**
     * Retrieves a policy based on the provided customer and policy ID.
     *
     * @param customer The customer associated with the policy.
     * @param pid The ID of the policy to retrieve.
     * @return The retrieved policy.
     * @throws RuntimeException if no policy exists with the provided customer and policy ID.
     */
    public Policy getPolicy(Customer customer, Long pid){
        Optional<Policy> request = policyRepository.findPolicyByCustomerAndPid(customer,pid);
        if (request.isEmpty()){
            throw new RuntimeException("No such policy exists");
        }
        return request.get();
    }
}
