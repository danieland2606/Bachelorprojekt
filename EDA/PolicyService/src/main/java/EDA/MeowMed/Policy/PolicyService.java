package EDA.MeowMed.Policy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import EDA.MeowMed.Policy.Messaging.PolicyAddedSender;
import EDA.MeowMed.Policy.View.*;
import EDA.MeowMed.Policy.Storage.*;
import org.springframework.stereotype.Service;

@Service
public class PolicyService {

    private TemporaryStorage storage = new TemporaryStorage();

    private final PolicyAddedSender policyAddedSender;

    public PolicyService(PolicyAddedSender policyAddedSender) {
        this.policyAddedSender = policyAddedSender;
    }

    public List<PolicyOverview> getPolicyList(long customer_id, String objectOfInsuranceName, String startDate, String endDate, Integer coverage) {
        ArrayList<PolicyOverview> listOfPolicies = new ArrayList<>();
        for (PolicyView p : storage.getData().keySet()) {
            if (storage.getData().get(p) == customer_id &&
               (objectOfInsuranceName == null || p.getObjectOfInsurance().getName().equals(objectOfInsuranceName)) &&
               (startDate == null || p.getStartDate().equals(LocalDate.parse(startDate))) &&
               (endDate == null || p.getEndDate().equals(LocalDate.parse(endDate))) &&
               (coverage == null || p.getCoverage() == coverage)) {

                listOfPolicies.add(new PolicyOverview(p.getId(), p.getStartDate(), p.getEndDate(), p.getCoverage(), new ObjectOfInsuranceOverview(p.getObjectOfInsurance().getName())));
            }
        }
        return listOfPolicies;
    }

    public PolicyView getPolicyById(long customerID, long policyID) {
        for (PolicyView p : storage.getData().keySet()) {
            if (storage.getData().get(p) == customerID && p.getId() == policyID) {
                return p;
            }
        }
        return null;
    }

    public long addPolicy(long customerID, PolicyView policy) {
        long maxID = 0;
        for (PolicyView p : storage.getData().keySet()) {
            if (maxID < p.getId()) {
                maxID = p.getId();
            }
        }
        policy.setId(++maxID);
        storage.getData().put(policy, customerID);


        //TODO: Send Event to notification Service
        this.policyAddedSender.send();

        return maxID;
    }
}
