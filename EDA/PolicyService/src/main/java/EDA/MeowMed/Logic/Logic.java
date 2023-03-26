package EDA.MeowMed.Logic;

import EDA.MeowMed.Storage.TemporaryStorage;
import EDA.MeowMed.View.ObjectOfInsuranceOverview;
import EDA.MeowMed.View.PolicyOverview;
import EDA.MeowMed.View.PolicyView;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Logic {

    public TemporaryStorage storage;

    public Logic() {
        this.storage = new TemporaryStorage();
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
        for (PolicyView p : this.storage.getData().keySet()) {
            if (this.storage.getData().get(p) == customerID && p.getId() == policyID) {
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
        

        return maxID;
    }
}
