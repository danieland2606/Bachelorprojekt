package EDA.MeowMed.Logic;

import java.util.*;

import EDA.MeowMed.Exceptions.DatabaseAccessException;
import EDA.MeowMed.Exceptions.InvalidPolicyDataException;
import EDA.MeowMed.Exceptions.ObjectNotFoundException;
import EDA.MeowMed.Module.PremiumCalculator;
import EDA.MeowMed.Persistence.*;
import EDA.MeowMed.Persistence.Entity.CatRace;
import events.customer.CustomerChangedEvent;
import events.customer.CustomerCreatedEvent;
import EDA.MeowMed.Messaging.PolicySender;
import EDA.MeowMed.Persistence.Entity.Customer;
import EDA.MeowMed.Persistence.Entity.Policy;
import EDA.MeowMed.Rest.PremiumCalculationData;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import events.policy.PolicyChangedEvent;
import events.policy.PolicyCreatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;

@Service
public class PolicyService {

    private final PolicySender policySender;

    private final PolicyRepository policyRepository;

    private final ObjectOfInsuranceRepository objectOfInsuranceRepository;

    private final CustomerRepository customerRepository;

    private final AddressRepository addressRepository;

    private final CatRaceRepository catRaceRepository;

    /**
     * Constructor for PolicyService class.
     *
     * @param policySender                a sender object used to send notifications when a new policy is added
     * @param policyRepository            a repository object used to access policies data
     * @param objectOfInsuranceRepository a repository object used to access object of insurance data
     * @param customerRepository          a repository object used to access customer data
     * @param addressRepository           a repository object used to access address data
     */
    @Autowired
    public PolicyService(PolicySender policySender,
                         PolicyRepository policyRepository,
                         ObjectOfInsuranceRepository objectOfInsuranceRepository,
                         CustomerRepository customerRepository,
                         AddressRepository addressRepository,
                         CatRaceRepository catRaceRepository) {
        this.policySender = policySender;
        this.policyRepository = policyRepository;
        this.objectOfInsuranceRepository = objectOfInsuranceRepository;
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
        this.catRaceRepository = catRaceRepository;
        this.setUp();
    }

    private void setUp() {
        ArrayList<CatRace> entities = new ArrayList<>();
        entities.add(new CatRace("siamese", 12, 15, 4, 7, 2, new String[]{"seal", "blau", "lilac", "creme"}));
        entities.add(new CatRace("perser", 12, 16, 4, 7, 3, new String[]{"weiß", "schildpatt", "schwarz"}));
        entities.add(new CatRace("bengal", 12, 16, 4, 6, 4, new String[]{"braun", "schildpatt", "marmor"}));
        entities.add(new CatRace("maine-cone", 12, 15, 5, 10, 2, new String[]{"grau", "braun", "weiß"}));
        entities.add(new CatRace("sphynx", 12, 15, 4, 6, 5, new String[]{}));
        entities.add(new CatRace("scottish Fold", 12, 15, 4, 6, 6, new String[]{}));
        entities.add(new CatRace("british-shorthair", 12, 15, 4, 6, 0, new String[]{}));
        entities.add(new CatRace("abyssinian", 12, 15, 3, 5, 4, new String[]{"rot", "schildpatt", "zimt"}));
        entities.add(new CatRace("ragdoll", 12, 15, 4, 7, 3, new String[]{"blau", "seal", "lilac", "schildpatt"}));
        this.catRaceRepository.saveAll(entities);
    }


    /**
     * Finds a policy by its customer ID and policy ID, and returns a filtered MappingJacksonValue of the policy.
     *
     * @param customerID the ID of the customer who owns the policy
     * @param policyID   the ID of the policy to be retrieved
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
            if (policy == null) {
                throw new ObjectNotFoundException("Policy ID " + policyID + " not found for customer ID " + customerID);
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
     *
     * @param customerID The ID of the customer for whom the policy is being added.
     * @param policy     The policy to be added.
     * @return A {@link MappingJacksonValue} object containing the policy that was added.
     * @throws ObjectNotFoundException if the customer with the given ID does not exist.
     * @throws DatabaseAccessException if there is an error accessing the database.
     */
    public MappingJacksonValue addPolicy(long customerID, Policy policy) throws ObjectNotFoundException, DatabaseAccessException, InvalidPolicyDataException {
        try {
            /* No Policies for "sehr verspielte" cats */
            if (policy.getObjectOfInsurance().getPersonality().contains("sehr verspielt")) {
                throw new InvalidPolicyDataException("It is not possible to create a Policy for 'sehr verspielte' cats.");
            }

            // Set the customer and premium for the policy
            policy.setCustomer(this.getCustomer(customerID));

            /* No Policies for "arbeitslose" customer */
            if (policy.getCustomer().getEmploymentStatus().equals("arbeitslos")) {
                throw new InvalidPolicyDataException("It is not possible to create a Policy for 'arbeitlose' Customer.");
            }

            // All created Policies are active by default, policies that have invalid data will not be created in the first place
            policy.setActive(true);

            policy.setPremium(this.getPremium(policy.getCustomer(), policy));
            System.out.println("Premium: " + policy.getPremium());

            // Save the object of insurance and policy
            this.objectOfInsuranceRepository.save(policy.getObjectOfInsurance());
            this.policyRepository.save(policy);

            // Create a wrapper for the policy and set the filters
            MappingJacksonValue wrapper = new MappingJacksonValue(this.policyRepository.save(policy));
            wrapper.setFilters(new SimpleFilterProvider()
                    .addFilter("policyFilter", SimpleBeanPropertyFilter.filterOutAllExcept("id"))
                    .setFailOnUnknownId(false));

            // Send a message to notify that the policy was created
            this.policySender.sendPolicyCreated(new PolicyCreatedEvent(policy.toPojo()));

            return wrapper;
        } catch (DataAccessException ex) {
            throw new DatabaseAccessException("Error accessing the database: " + ex.getMessage());
        }
    }

    /**
     * Retrieves a list of policies for a customer with specified ID, with the option to select specific fields to include
     *
     * @param customerID the ID of the customer to retrieve policies for
     * @param fields     comma-separated list of fields to include in the response
     * @return a {@link MappingJacksonValue} object containing the list of policies retrieved
     * @throws ObjectNotFoundException if the customer with the specified ID does not exist
     * @throws DatabaseAccessException if there is an error accessing the database
     */
    public MappingJacksonValue getPolicyList(long customerID, String fields) throws ObjectNotFoundException, DatabaseAccessException {
        try {
            if (!this.doesCustomerExist(customerID)) {
                throw new ObjectNotFoundException("The Customer with ID: " + customerID + " does not exist in the database.");
            }

            // retrieve list of policies for the customer
            List<Policy> list = this.policyRepository.getPolicyList(customerID);

            // throw ObjectNotFoundException if list is empty
            if (list == null) {
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
                if (result.contains("objectOfInsurance.")) {
                    ooIList.add(result.substring(18));
                    policyList.remove(result);
                    containsOoI = true;
                } else {
                    policyList.add(result);
                }
            }

            // add objectOfInsurance field if it was included in the fields parameter
            if (containsOoI) {
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
     *
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
     *
     * @param calculationData the calculationData Object
     * @return the calculated Premium
     * @throws ObjectNotFoundException if the customer does not exist
     */
    public double getPremium(PremiumCalculationData calculationData) throws ObjectNotFoundException {
        return this.getPremium(this.getCustomer(calculationData.getCustomerId()), calculationData.getPolicy());
    }

    /**
     * This method calculates the premium for a given customer and policy using the PremiumCalculator.
     * It takes in a Customer object and a Policy object as parameters and returns a double value
     * representing the total premium.
     *
     * @param customer The Customer object representing the customer who is taking the policy.
     * @param policy   The Policy object representing the policy being taken by the customer.
     * @return A double value representing the total premium for the given policy and customer.
     * @Test Die Tests waren erfolgreich und ich habe die Ergebnisse mit den vorgegebenen Vorlagen verglichen. Alles hat gut funktioniert
     * TODO Fehlerbehandlung muss hinzugefügt werden
     */
    public double getPremium(Customer customer, Policy policy) {
        // Get the cat race from the catRaceRepository based on the policy's object of insurance race
        CatRace catRace = catRaceRepository.findByRace(policy.getObjectOfInsurance().getRace());
        // If the cat race is not found, return 0 as the premium
        if (catRace == null) {
            System.out.println("Katzenrasse leer!");
            return 0;
        }
        // Calculate the base price for the policy using the PremiumCalculator
        double basePrice = PremiumCalculator.calculateBasePrice(policy);
        System.out.println("base Preise: " + basePrice);
        // Calculate the total price of the policy by adding the prices for each factor
        double totalPrice = basePrice;
        totalPrice += PremiumCalculator.calculateWeightPrice(policy.getObjectOfInsurance().getWeight(), catRace);
        System.out.println("base Preise nach WeightPreise: " + totalPrice); //Funktioniert

        totalPrice += PremiumCalculator.calculateIllnessFactorPrice(catRace);
        System.out.println("base Preise nach IllnessFactorPrice: " + totalPrice); //Funktioniert

        totalPrice += PremiumCalculator.calculateEnvironmentPrice(policy.getObjectOfInsurance().getEnvironment(), basePrice);
        System.out.println("base Preise nach EnvironmentPrice: " + totalPrice); //Funktioniert

        totalPrice += PremiumCalculator.calculateAgePrice(policy.getObjectOfInsurance().getDateOfBirth(), catRace, basePrice);
        System.out.println("base Preise nach AgePrice: " + totalPrice);  //Funktioniert

        totalPrice += PremiumCalculator.calculateCastrationPrice(policy.getObjectOfInsurance().isCastrated());
        System.out.println("base Preise nach CastrationPrice: " + totalPrice); //Funktioniert

        totalPrice += PremiumCalculator.calculatePostalCodePrice(customer.getAddress().getPostalCode(), basePrice);
        System.out.println("base Preise nach PostalCodePrice: " + totalPrice); //Funktioniert

        totalPrice += PremiumCalculator.applyDogOwnerSurcharge(customer, basePrice);
        System.out.println("base Preise nach DogOwner: " + totalPrice);  //Funktioniert

        totalPrice = PremiumCalculator.roundToTwoDecimal(totalPrice);
        System.out.println("base Preise nach roundToTwoDecimal: " + totalPrice);
        return totalPrice;
    }


    /**
     * Checks if a customer with the given ID exists in the database.
     *
     * @param customerID The ID of the customer to check
     * @return true if the customer exists, false otherwise
     */
    private boolean doesCustomerExist(Long customerID) {
        Optional<Customer> customerForPolicy = this.customerRepository.findById(customerID);
        return customerForPolicy.isPresent();
    }

    /**
     * Get Customer by CustomerID
     *
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

    /**

     Updates an existing Policy with the given policyID and new policy data.
     @param policyID the ID of the policy to update
     @param newPolicy the new policy data to update the policy with
     @throws ObjectNotFoundException if the policy with the given policyID doesn't exist in the database
     */
    public void updatePolicy(Long policyID, Policy newPolicy) throws ObjectNotFoundException {
        Optional<Policy> p = this.policyRepository.findById(policyID);
        if (p.isEmpty()) {
            throw new ObjectNotFoundException("The given Policy with PolicyID: " + policyID + " does not exist in the database.");
        }
        Policy oldPolicy = new Policy(p.get());
        Policy persistentPolicy = p.get();
        persistentPolicy.setCoverage(newPolicy.getCoverage());
        persistentPolicy.getObjectOfInsurance().setPersonality(newPolicy.getObjectOfInsurance().getPersonality());
        Customer customer = persistentPolicy.getCustomer();
        persistentPolicy.setPremium(this.getPremium(customer, persistentPolicy));
        boolean premiumChanged = Math.abs(persistentPolicy.getPremium() - oldPolicy.getPremium()) > 0.0001;
        if (persistentPolicy.getObjectOfInsurance().getPersonality().equals("sehr verspielt")) {
            persistentPolicy.setActive(false);
            this.policySender.sendPolicyCancelled(new PolicyChangedEvent(persistentPolicy.toPojo()));
        } else if (premiumChanged) {
            this.policySender.sendPolicyChanged(new PolicyChangedEvent(persistentPolicy.toPojo()));
        }
        policyRepository.save(persistentPolicy);
        objectOfInsuranceRepository.save(persistentPolicy.getObjectOfInsurance());
//        this.updatePolicyDataBasedOnNewInformation(persistentPolicy, cancelPolicy);
    }

    /**
     Updates the customer data based on the provided event, including their personal information,
     address, and employment status. If the customer's employment status changes to "arbeitslos",
     all their policies will be canceled. Then, it flushes the changes to the customer repository and
     recalculates the premium for all the customer's policies.
     @param customerChangedEvent The event that contains the new customer data
     */
    public void updateCustomer(CustomerChangedEvent customerChangedEvent) throws ObjectNotFoundException{
        Customer customer = this.getCustomer(customerChangedEvent.getCid());
        customer.setFirstName(customerChangedEvent.getFirstName());
        customer.setLastName(customerChangedEvent.getLastName());
        customer.setFormOfAddress(customerChangedEvent.getFormOfAddress());
        customer.setTitle(customerChangedEvent.getTitle());
        customer.setDogOwner(customerChangedEvent.getDogOwner());
        customer.getAddress().setPostalCode(customerChangedEvent.getAddress().getPostalCode());
        customer.setEmail(customerChangedEvent.getEmail());
        customer.setEmploymentStatus(customerChangedEvent.getEmploymentStatus());
        this.addressRepository.save(customer.getAddress());
        this.customerRepository.save(customer);
//        this.customerRepository.flush(); Speichert customer nicht
        this.updateAllPoliciesOfCustomer(customerChangedEvent.getCid(), customer.getEmploymentStatus().equals("arbeitslos"));
    }

    /**
     Recalculates the premium for all policies associated with the given customer ID.
     Updates the premium for each policy if it has changed by more than 0.0001.
     Sends a notification for each policy with a changed premium.
     @param customerID The ID of the customer whose policies to recalculate.
     @param cancelPolicies Determines if policies will be set to cancelled.
     */
    public void updateAllPoliciesOfCustomer(Long customerID, boolean cancelPolicies) throws ObjectNotFoundException{
        for (Policy p : this.policyRepository.getPolicyList(customerID)) {
            if (p.isActive()) {
                this.updatePolicyDataBasedOnNewInformation(p, cancelPolicies);
            }
        }
        this.policyRepository.flush();
    }
    /**
     * TODO: Kommentare
     * @param newPolicy
     * @param cancelPolicy
     */
    private void updatePolicyDataBasedOnNewInformation(Policy newPolicy, boolean cancelPolicy) throws ObjectNotFoundException{
        System.out.println("Customer Postleitzahl: " + newPolicy.getCustomer().getAddress().getPostalCode());
        double newPremium = this.getPremium(newPolicy.getCustomer(), newPolicy);
        boolean premiumChanged = Math.abs(newPremium - newPolicy.getPremium()) > 0.0001;
        newPolicy.setPremium(newPremium);
        if (cancelPolicy) {
            newPolicy.setActive(false);
            //FIXME Art des Speicherns notwendig? Anders möglich?
            policyRepository.save(newPolicy);
            objectOfInsuranceRepository.save(newPolicy.getObjectOfInsurance());
            this.policySender.sendPolicyCancelled(new PolicyChangedEvent(newPolicy.toPojo()));
        } else if(premiumChanged) {
            policyRepository.save(newPolicy);
            objectOfInsuranceRepository.save(newPolicy.getObjectOfInsurance());
            this.policySender.sendPolicyChanged(new PolicyChangedEvent(newPolicy.toPojo()));
        }
    }
}