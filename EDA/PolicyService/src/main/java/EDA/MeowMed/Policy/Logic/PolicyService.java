package EDA.MeowMed.Policy.Logic;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import EDA.MeowMed.Policy.Persistence.AddressRepository;
import EDA.MeowMed.Policy.Persistence.CustomerRepository;
import EDA.MeowMed.Policy.Persistence.Entity.Customer;
import EDA.MeowMed.Policy.Persistence.Entity.Policy;
import EDA.MeowMed.Policy.Messaging.PolicyAddedSender;
import EDA.MeowMed.Policy.Persistence.ObjectOfInsuranceRepository;
import EDA.MeowMed.Policy.Persistence.PolicyRepository;
import EDA.MeowMed.Policy.View.*;
import org.hibernate.boot.model.relational.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.messaging.MessagingException;
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


    // public PolicyOverviewProjection findPolicyByCustomerIDAndPolicyID(long customerID, long policyID) {
    //     return this.policyRepository.findPolicyOverviewByCustomerIDAndPolicyID(customerID, policyID);
    // }


    /**
     This method searches for a "Policy" in the database based on its ID and the ID of the associated customer.
     It executes a JPQL query to load the "id", "coverage", "startDate", "endDate", "objectOfInsurance", and
     "premium" attributes of the "Policy" that matches the specified conditions.

     @param customerID the ID of the associated customer

     @param policyID the ID of the "Policy"

     @return a "PolicyOverviewProjection" object that contains the requested attributes, or throws a "ChangeSetPersister.NotFoundException" if no matching "Policy" was found.

     @throws ChangeSetPersister.NotFoundException if the requested policy is not found.
     */
    public PolicyOverviewProjection findPolicyByCustomerIDAndPolicyID(long customerID, long policyID) throws ChangeSetPersister.NotFoundException {
        PolicyOverviewProjection policy = this.policyRepository.findPolicyOverviewByCustomerIDAndPolicyID(customerID, policyID);
        if (policy == null) {
            throw new ChangeSetPersister.NotFoundException();// TODO in Controller, oder nicht?
        }
        return policy;
    }




    /**
     Adds a policy for a customer and calculates its premium based on several factors.

     @param customerID The ID of the customer for whom the policy is added.

     @param policy The policy to be added.

     @return The added policy.

     @throws Exception If the customer with the given ID is not found.

     TODO: Throws müssen noch gemacht werden
     */

    public Policy addPolicy(long customerID, Policy policy) throws Exception {
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
        this.policyAddedSender.send(policy);
        return policy;
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
//    }


    //TODO: Bisschen darüber reden welche Parameter wir übergeben bekommen.
    public List<PolicyOverview> getPolicyList(long customerID, String objectOfInsuranceName, String startDate, String endDate, Integer coverage) {
        ArrayList<PolicyOverview> listOfPolicies = new ArrayList<>();
        for (Policy p : this.policyRepository.findAll()) { //TODO: filter this inside SQL-Query and not in Code
            if (p.getCustomer().getId() == customerID &&
                    (objectOfInsuranceName == null || p.getObjectOfInsurance().getName().equals(objectOfInsuranceName)) &&
                    (startDate == null || p.getStartDate().equals(LocalDate.parse(startDate))) &&
                    (endDate == null || p.getEndDate().equals(LocalDate.parse(endDate))) &&
                    (coverage == null || p.getCoverage() == coverage)) {
                listOfPolicies.add(new PolicyOverview(p.getId(), p.getStartDate(), p.getEndDate(), p.getCoverage(), new ObjectOfInsuranceOverview(p.getObjectOfInsurance().getName())));
            }
        }
        return listOfPolicies;
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


        return base + added;
    }
}
