package EDA.MeowMed.Policy.Logic;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import EDA.MeowMed.Policy.Persistence.AddressRepository;
import EDA.MeowMed.Policy.Persistence.CustomerRepository;
import EDA.MeowMed.Policy.Persistence.Entity.Customer;
import EDA.MeowMed.Policy.Persistence.Entity.Policy;
import EDA.MeowMed.Policy.Messaging.PolicyAddedSender;
import EDA.MeowMed.Policy.Persistence.ObjectOfInsuranceRepository;
import EDA.MeowMed.Policy.Persistence.PolicyRepository;
import EDA.MeowMed.Policy.View.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PolicyService {

    private final PolicyAddedSender policyAddedSender;

    private final PolicyRepository policyRepository;

    private final ObjectOfInsuranceRepository objectOfInsuranceRepository;

    private final CustomerRepository customerRepository;

    private final AddressRepository addressRepository;

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

    public Policy getPolicyById(long customerID, long policyID) {
        for (Policy p : this.policyRepository.findAll()) { //TODO: filter inside SQL-Query and not in Code
            if (p.getCustomer().getId() == customerID && p.getId() == policyID) {
                return p; //TODO: this does also return the customer, maybe exclude the customer?
            }
        }
        return null;
    }

    public Long addPolicy(long customerID, Policy policy) {
        Customer customerForPolicy = null;
        for (Customer c : this.customerRepository.findAll()) { //TODO: Filter with SQL-Query and not with Code
            if (c.getId() == customerID) {
                customerForPolicy = c;
            }
        }

        policy.setCustomer(customerForPolicy);

        policy.setPremium(this.getPremium(policy.getCustomer(), policy));

        this.objectOfInsuranceRepository.save(policy.getObjectOfInsurance());
        this.policyRepository.save(policy);

        this.policyAddedSender.send(policy);

        return policy.getId();
    }

    public void addNewCustomer(Customer customer) {
        /* For testing, add these two entries manually into the database:
         * INSERT INTO address VALUES (1, "Hannover", "30457", "Ricklinger Stadtweg");
         * INSERT INTO customer VALUES (1, "Bank Bla Bli Blub", '2000-12-19', "yeet@yeet.com", "Student", "YA", "Yeet", "Forever Alone", "123456789", 1);
         */
        this.addressRepository.save(customer.getAddress());
        this.customerRepository.save(customer);
    }

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
