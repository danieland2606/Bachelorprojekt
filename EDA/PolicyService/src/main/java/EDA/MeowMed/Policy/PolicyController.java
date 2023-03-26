package EDA.MeowMed.Policy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import EDA.MeowMed.Policy.View.*;
import EDA.MeowMed.Policy.Storage.*;

public class PolicyController {

    public static TemporaryStorage storage = new TemporaryStorage();

    public static List<PolicyOverview> getPolicyList(long customer_id, String objectOfInsuranceName, String startDate, String endDate, Integer coverage) {
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

    public static PolicyView getPolicyById(long customerID, long policyID) {
        for (PolicyView p : storage.getData().keySet()) {
            if (storage.getData().get(p) == customerID && p.getId() == policyID) {
                return p;
            }
        }
        return null;
    }

    public static long addPolicy(long customerID, PolicyView policy) {
        long maxID = 0;
        for (PolicyView p : storage.getData().keySet()) {
            if (maxID < p.getId()) {
                maxID = p.getId();
            }
        }
        policy.setId(++maxID);
        storage.getData().put(policy, customerID);


        //TODO: Send Event to notification Service
        

        return maxID;
    }
}
