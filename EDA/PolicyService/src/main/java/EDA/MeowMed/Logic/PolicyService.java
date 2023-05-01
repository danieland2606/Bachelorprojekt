package EDA.MeowMed.Logic;

import java.util.*;

import EDA.MeowMed.Exceptions.DatabaseAccessException;
import EDA.MeowMed.Exceptions.ObjectNotFoundException;
import events.customer.CustomerCreatedEvent;
import events.policy.PolicyCreatedEvent;
import EDA.MeowMed.Messaging.PolicyCreatedSender;
import EDA.MeowMed.Persistence.CustomerRepository;
import EDA.MeowMed.Persistence.Entity.Customer;
import EDA.MeowMed.Persistence.Entity.Policy;
import EDA.MeowMed.Persistence.ObjectOfInsuranceRepository;
import EDA.MeowMed.Persistence.PolicyRepository;
import EDA.MeowMed.Persistence.AddressRepository;
import EDA.MeowMed.Rest.PremiumCalculationData;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;

@Service
public class PolicyService {

    private final PolicyCreatedSender policyCreatedSender;

    private final PolicyRepository policyRepository;

    private final ObjectOfInsuranceRepository objectOfInsuranceRepository;

    private final CustomerRepository customerRepository;

    private final AddressRepository addressRepository;

    /**
     * Constructor for PolicyService class.
     * @param policyCreatedSender a sender object used to send notifications when a new policy is added
     * @param policyRepository a repository object used to access policies data
     * @param objectOfInsuranceRepository a repository object used to access object of insurance data
     * @param customerRepository a repository object used to access customer data
     * @param addressRepository a repository object used to access address data
     */
    @Autowired
    public PolicyService(PolicyCreatedSender policyCreatedSender,
                         PolicyRepository policyRepository,
                         ObjectOfInsuranceRepository objectOfInsuranceRepository,
                         CustomerRepository customerRepository,
                         AddressRepository addressRepository) {
        this.policyCreatedSender = policyCreatedSender;
        this.policyRepository = policyRepository;
        this.objectOfInsuranceRepository = objectOfInsuranceRepository;
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
    }

    /**
     * Finds a policy by its customer ID and policy ID, and returns a filtered MappingJacksonValue of the policy.
     * @param customerID the ID of the customer who owns the policy
     * @param policyID the ID of the policy to be retrieved
     * @return a MappingJacksonValue object containing the filtered policy retrieved
     * @throws ObjectNotFoundException if the customer or policy is not found
     * @throws DatabaseAccessException if there is an error accessing the database
     */
    public MappingJacksonValue findPolicyByCustomerIDAndPolicyID(long customerID, long policyID) throws ObjectNotFoundException, DatabaseAccessException {
        try {
            // Check if the customer exists
            if (!doesCustomerExist(customerID)) {
                throw new ObjectNotFoundException("Customer with ID " + customerID + " not found");
            }

            // Retrieve the policy by customer ID and policy ID
            Policy policy = this.policyRepository.findPolicyByCustomerIDAndPolicyID(customerID, policyID);

            // Throw an ObjectNotFoundException if the policy is not found
            if(policy == null){
                throw new ObjectNotFoundException("Policy ID "+ policyID +" not found for customer ID " + customerID);
            }

            // Filter the policy and return it as a MappingJacksonValue
            MappingJacksonValue wrapper = new MappingJacksonValue(policy);
            wrapper.setFilters(new SimpleFilterProvider()
                    .addFilter("policyFilter", SimpleBeanPropertyFilter.serializeAllExcept("id", "customer"))
                    .addFilter("objectOfInsuranceFilter", SimpleBeanPropertyFilter.serializeAll())
                    .setFailOnUnknownId(false));
            return wrapper;
        } catch (DataAccessException ex) {
            // Throw a DatabaseAccessException if there is an error accessing the database
            throw new DatabaseAccessException("Error accessing the database: " + ex.getMessage());
        }
    }

    /**
     * Adds a policy for the specified customer.
     * @param customerID The ID of the customer for whom the policy is being added.
     * @param policy The policy to be added.
     * @return A {@link MappingJacksonValue} object containing the policy that was added.
     * @throws ObjectNotFoundException if the customer with the given ID does not exist.
     * @throws DatabaseAccessException if there is an error accessing the database.
     */
    public MappingJacksonValue addPolicy(long customerID, Policy policy) throws ObjectNotFoundException, DatabaseAccessException {
        try {
            // Set the customer and premium for the policy
            policy.setCustomer(this.getCustomer(customerID));
            policy.setPremium(this.getPremium(policy.getCustomer(), policy));

            // Save the object of insurance and policy
            this.objectOfInsuranceRepository.save(policy.getObjectOfInsurance());
            this.policyRepository.save(policy);

            // Create a wrapper for the policy and set the filters
            MappingJacksonValue wrapper = new MappingJacksonValue(this.policyRepository.save(policy));
            wrapper.setFilters(new SimpleFilterProvider()
                    .addFilter("policyFilter", SimpleBeanPropertyFilter.filterOutAllExcept("id"))
                    .setFailOnUnknownId(false));

            // Send a message to notify that the policy was created
            this.policyCreatedSender.send(policy.createPolicyCreatedEvent());

            return wrapper;
        } catch (DataAccessException ex) {
            throw new DatabaseAccessException("Error accessing the database: " + ex.getMessage());
        }
    }

    /**
     * Retrieves a list of policies for a customer with specified ID, with the option to select specific fields to include
     * @param customerID the ID of the customer to retrieve policies for
     * @param fields comma-separated list of fields to include in the response
     * @return a {@link MappingJacksonValue} object containing the list of policies retrieved
     * @throws ObjectNotFoundException if the customer with the specified ID does not exist
     * @throws DatabaseAccessException if there is an error accessing the database
     */
    public MappingJacksonValue getPolicyList(long customerID, String fields) throws ObjectNotFoundException, DatabaseAccessException {
        try {
            // check if customer exists
            if (!doesCustomerExist(customerID)) {
                throw new ObjectNotFoundException("Customer with ID " + customerID + " not found");
            }

            // retrieve list of policies for the customer
            List<Policy> list = this.policyRepository.getPolicyList(customerID);

            // throw ObjectNotFoundException if list is empty
            if(list == null ){
                throw new ObjectNotFoundException("Policy list not found for customer ID " + customerID);
            }

            // create MappingJacksonValue wrapper for the list of policies
            MappingJacksonValue wrapper = new MappingJacksonValue(list);

            // parse the fields parameter to separate policy fields from objectOfInsurance fields
            String[] fieldList = fields.split(",");
            List<String> policyList = new ArrayList<>();
            List<String> ooIList = new ArrayList<>();

            boolean containsOoI = false;
            for (String result : fieldList) {
                if(result.contains("objectOfInsurance.")){
                    ooIList.add(result.substring(18));
                    policyList.remove(result);
                    containsOoI = true;
                } else {
                    policyList.add(result);
                }
            }

            // add objectOfInsurance field if it was included in the fields parameter
            if(containsOoI) {
                policyList.add("objectOfInsurance");
            }
            policyList.add("id");

            // set filters on the wrapper to include only specified fields
            wrapper.setFilters(new SimpleFilterProvider()
                    .addFilter("policyFilter", SimpleBeanPropertyFilter.filterOutAllExcept(Set.copyOf(policyList)))
                    .addFilter("objectOfInsuranceFilter", SimpleBeanPropertyFilter.filterOutAllExcept(Set.copyOf(ooIList)))
                    .setFailOnUnknownId(false));
            return wrapper;
        } catch (DataAccessException e) {
            throw new DatabaseAccessException("Error accessing database for policy list of customer ID " + customerID, e);
        }
    }

    /**
     * Adds a new customer to the database based on the information provided in a CustomerCreatedEvent.
     * The address of the customer is also saved in the database.
     * @param customerCreatedEvent an event containing the information of the new customer
     * @throws DataAccessException if there's an error accessing the database
     */
    public void addNewCustomer(CustomerCreatedEvent customerCreatedEvent) throws DataAccessException {
        Customer customer = new Customer(customerCreatedEvent);
        // Save the address of the customer in the database
        this.addressRepository.save(customer.getAddress());
        // Save the customer in the database
        this.customerRepository.save(customer);
    }

    /**
     * Wrapper for {@link EDA.MeowMed.Logic.PolicyService#getPremium(Customer customer, Policy policy) getPremium(Customer customer, Policy policy)}
     * @param calculationData the calculationData Object
     * @return the calculated Premium
     * @throws ObjectNotFoundException if the customer does not exist
     */
    public double getPremium(PremiumCalculationData calculationData) throws ObjectNotFoundException {
        return this.getPremium(this.getCustomer(calculationData.getCustomerId()), calculationData.getPolicy());
    }

    /**
     * Calculates the premium for a given customer and policy
     * @param customer the customer for which the premium is being calculated
     * @param policy the policy for which the premium is being calculated
     * @return the calculated premium for the given customer and policy
     */
    public double getPremium(Customer customer, Policy policy) {
        double factor = 0.015;
        if (policy.getObjectOfInsurance().getColor().equals("Schwarz")) {
            factor = 0.02;
        }
        double base = policy.getCoverage() * factor;

        double added = 0.0;

        /* +5€ pro Kilo abweichend vom Durchschnitt */
        //TODO

        /* Einberechnung der Krankheitswahrscheinlichkeit */
        //TODO

        /* Draußenkatze */
        if(policy.getObjectOfInsurance().getEnvironment().equals("draußen")) {
            added += 10 * base / 100;
        }

        /* Alter der Katze im oberen Quantil des Durchschnittsalters oder älter */
        //TODO

        /* Alter der Katze <= 2? */
        //TODO

        /* Kastriert? */
        if (policy.getObjectOfInsurance().isCastrated()) {
            added += 5;
        }

        /* Postal Code Racism */
        //TODO

        return base + added;
    }

    /**
     * Checks if a customer with the given ID exists in the database.
     * @param customerID The ID of the customer to check
     * @return true if the customer exists, false otherwise
     */
    private boolean doesCustomerExist(Long customerID) {
        Optional<Customer> customerForPolicy = this.customerRepository.findById(customerID);
        return customerForPolicy.isPresent();
    }

    /**
     * Get Customer by CustomerID
     * @param customerID the id of the customer
     * @return the customer object
     * @throws ObjectNotFoundException when the Customer does not exist
     */
    private Customer getCustomer(Long customerID) throws ObjectNotFoundException {
        Optional<Customer> customerForPolicy = this.customerRepository.findById(customerID);
        if (customerForPolicy.isEmpty()) {
            throw new ObjectNotFoundException("Customer with ID: " + customerID + " does not exist.");
        }
        return customerForPolicy.get();
    }
}