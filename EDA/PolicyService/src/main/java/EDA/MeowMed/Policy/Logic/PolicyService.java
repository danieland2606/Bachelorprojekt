package EDA.MeowMed.Policy.Logic;

import java.util.*;
import EDA.MeowMed.Policy.Persistence.AddressRepository;
import EDA.MeowMed.Policy.Persistence.CustomerRepository;
import EDA.MeowMed.Policy.Persistence.Entity.Customer;
import EDA.MeowMed.Policy.Persistence.Entity.Policy;
import EDA.MeowMed.Policy.Messaging.PolicyAddedSender;
import EDA.MeowMed.Policy.Persistence.ObjectOfInsuranceRepository;
import EDA.MeowMed.Policy.Persistence.PolicyRepository;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;

@Service
public class PolicyService {

    private final PolicyAddedSender policyAddedSender;

    private final PolicyRepository policyRepository;

    private final ObjectOfInsuranceRepository objectOfInsuranceRepository;

    private final CustomerRepository customerRepository;

    private final AddressRepository addressRepository;

    /**
     Constructor for PolicyService class.

     @param policyAddedSender a sender object used to send notifications when a new policy is added

     @param policyRepository a repository object used to access policies data

     @param objectOfInsuranceRepository a repository object used to access object of insurance data

     @param customerRepository a repository object used to access customer data

     @param addressRepository a repository object used to access address data
     */
    @Autowired
    public PolicyService(PolicyAddedSender policyAddedSender,
                         PolicyRepository policyRepository,
                         ObjectOfInsuranceRepository objectOfInsuranceRepository,
                         CustomerRepository customerRepository,
                         AddressRepository addressRepository) {
        this.policyAddedSender = policyAddedSender;
        this.policyRepository = policyRepository;
        this.objectOfInsuranceRepository = objectOfInsuranceRepository;
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
    }

    /**
     This method searches for a "Policy" in the database based on its ID and the ID of the associated customer.
     It executes a JPQL query to load the "id", "coverage", "startDate", "endDate", "objectOfInsurance", and
     "premium" attributes of the "Policy" that matches the specified conditions.

     @param customerID the ID of the associated customer

     @param policyID the ID of the "Policy"

     @return a "PolicyOverviewProjection" object that contains the requested attributes, or throws a "ChangeSetPersister.NotFoundException" if no matching "Policy" was found.

     @throws ChangeSetPersister.NotFoundException if the requested policy is not found.
     */
    public MappingJacksonValue findPolicyByCustomerIDAndPolicyID(long customerID, long policyID) throws ChangeSetPersister.NotFoundException {
        Policy policy = this.policyRepository.findPolicyByCustomerIDAndPolicyID(customerID, policyID);
        if (policy == null) {
            throw new ChangeSetPersister.NotFoundException();
        }
        MappingJacksonValue wrapper = new MappingJacksonValue(policy);
        wrapper.setFilters(new SimpleFilterProvider()
                .addFilter("policyFilter", SimpleBeanPropertyFilter.serializeAllExcept("id", "customer"))
                .addFilter("objectOfInsuranceFilter", SimpleBeanPropertyFilter.serializeAll())
                .setFailOnUnknownId(false));
        return wrapper;
    }

    /**
     Adds a policy for a customer and calculates its premium based on several factors.

     @param customerID The ID of the customer for whom the policy is added.

     @param policy The policy to be added.

     @return The added policy.

     @throws Exception If the customer with the given ID is not found.
     */
    public MappingJacksonValue addPolicy(long customerID, Policy policy) throws Exception {
        Optional<Customer> customerForPolicy = null;
        customerForPolicy = this.customerRepository.findById(customerID);
        if(!customerForPolicy.isPresent())
        {
            throw new ChangeSetPersister.NotFoundException();
        }
        policy.setCustomer(customerForPolicy.get());
        policy.setPremium(this.getPremium(policy.getCustomer(), policy));
        this.objectOfInsuranceRepository.save(policy.getObjectOfInsurance());
        this.policyRepository.save(policy);
        MappingJacksonValue wrapper = new MappingJacksonValue(this.policyRepository.save(policy));
        wrapper.setFilters(new SimpleFilterProvider()
                .addFilter("policyFilter", SimpleBeanPropertyFilter.filterOutAllExcept("id"))
                .setFailOnUnknownId(false));
        this.policyAddedSender.send(policy);
        return wrapper;
    }
//    public Policy addPolicy2(long customerID, Policy policy) throws ChangeSetPersister.NotFoundException, DataAccessException, MessagingException {
//        Optional<Customer> customerForPolicy = this.customerRepository.findById(customerID);
//        if (!customerForPolicy.isPresent()) {
//            throw new ChangeSetPersister.NotFoundException().;
//        }
//        policy.setCustomer(customerForPolicy.get());
//        policy.setPremium(this.getPremium(policy.getCustomer(), policy));
//        try {
//            this.objectOfInsuranceRepository.save(policy.getObjectOfInsurance());
//            this.policyRepository.save(policy);
//        } catch (DataAccessException ex) {
//            throw new Exception("Error saving policy data to the database", ex);
//        }
//        try {
//            this.policyAddedSender.send(policy);
//        } catch (MessagingException ex) {
//            throw new MessagingException("Error sending policy added message", ex);
//        }
//        return policy;
//    } //TODO: die Fehlermeldungen im auskommentierten Code soll in die Funktion rein

    public MappingJacksonValue getPolicyList(long customerID, String fields) {
        MappingJacksonValue wrapper = new MappingJacksonValue(this.policyRepository.getPolicyList(customerID));

        List<String> fieldList = Arrays.asList(fields.split(","));
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
        if(containsOoI) {
            policyList.add("objectOfInsurance");
        }
        policyList.add("id");

        wrapper.setFilters(new SimpleFilterProvider()
                .addFilter("policyFilter", SimpleBeanPropertyFilter.filterOutAllExcept(Set.copyOf(policyList)))
                .addFilter("objectOfInsuranceFilter", SimpleBeanPropertyFilter.filterOutAllExcept(Set.copyOf(ooIList)))
                .setFailOnUnknownId(false));
        return wrapper;
    }

    /**
     Adds a new customer to the system, along with their associated address information.
     @param customer The customer object to be added to the system.

     //Catch von diesem Fehler ist TODO in Controller, oder nicht?
     @throws InvalidInputException If the customer object is missing any required fields, or if the address object is missing any required fields.

     //Catch von diesem Fehler ist TODO in Controller, oder nicht?
     @throws DatabaseAccessException If there is an error accessing the database during the save operation.

     //Catch von diesem Fehler ist TODO in Controller, oder nicht?
     @throws AddressValidationException If there is an error validating the address object before saving.
     */
    public void addNewCustomer(Customer customer) {
        this.addressRepository.save(customer.getAddress());
        this.customerRepository.save(customer);
    }

    /**
     Calculates the premium for a given customer and policy

     @param customer the customer for which the premium is being calculated

     @param policy the policy for which the premium is being calculated

     @return the calculated premium for the given customer and policy
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


        return base + added; //TODO: als JSON in vernünftiger Form returnen
    }
}
