package EDA.MeowMed.Policy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import EDA.MeowMed.Policy.Entity.CustomerEntity;
import EDA.MeowMed.Policy.Entity.PolicyEntity;
import EDA.MeowMed.Policy.Messaging.PolicyAddedSender;
import EDA.MeowMed.Policy.View.*;
import org.springframework.stereotype.Service;

@Service
public class PolicyService {

    private TemporaryStorage storage = new TemporaryStorage();

    private final PolicyAddedSender policyAddedSender;

    public PolicyService(PolicyAddedSender policyAddedSender) {
        this.policyAddedSender = policyAddedSender;
    }

    public List<PolicyOverview> getPolicyList(long customerID, String objectOfInsuranceName, String startDate, String endDate, Integer coverage) {
        if(!this.storage.getCustomerData().containsKey(customerID)) {
            return null;
        }
        ArrayList<PolicyOverview> listOfPolicies = new ArrayList<>();
        for (PolicyEntity p : storage.getPolicyData().keySet()) {
            if (storage.getPolicyData().get(p) == customerID &&
               (objectOfInsuranceName == null || p.getObjectOfInsurance().getName().equals(objectOfInsuranceName)) &&
               (startDate == null || p.getStartDate().equals(LocalDate.parse(startDate))) &&
               (endDate == null || p.getEndDate().equals(LocalDate.parse(endDate))) &&
               (coverage == null || p.getCoverage() == coverage)) {

                listOfPolicies.add(new PolicyOverview(p.getId(), p.getStartDate(), p.getEndDate(), p.getCoverage(), new ObjectOfInsuranceOverview(p.getObjectOfInsurance().getName())));
            }
        }
        return listOfPolicies;
    }

    public PolicyEntity getPolicyById(long customerID, long policyID) {
        if (!this.storage.getCustomerData().containsKey(customerID)) {
            return null;
        }
        for (PolicyEntity p : storage.getPolicyData().keySet()) {
            if (storage.getPolicyData().get(p) == customerID && p.getId() == policyID) {
                return p;
            }
        }
        return null;
    }

    public Long addPolicy(long customerID, PolicyEntity policy) {
        if (!this.storage.getCustomerData().containsKey(customerID)) {
            return null;
        }
        long maxID = 0;
        for (PolicyEntity p : storage.getPolicyData().keySet()) {
            if (maxID < p.getId()) {
                maxID = p.getId();
            }
        }
        policy.setId(++maxID);

        policy.setPremium(getPremium(this.storage.getCustomerData().get(customerID), policy));

        storage.getPolicyData().put(policy, customerID);

        this.policyAddedSender.send(policy);

        return maxID;
    }

    public void addNewCustomer(CustomerEntity customer) {
        if (this.storage.getCustomerData().containsKey(customer.getId())) {
            System.out.println("Customer already exists, nothing changed!");
        } else {
            this.storage.getCustomerData().put(customer.getId(), customer);
        }
    }

    public double getPremium(CustomerEntity customer, PolicyEntity policy) {
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
