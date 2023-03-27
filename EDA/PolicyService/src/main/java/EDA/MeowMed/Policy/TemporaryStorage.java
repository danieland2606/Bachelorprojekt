package EDA.MeowMed.Policy;

import EDA.MeowMed.Policy.Entity.CustomerEntity;
import EDA.MeowMed.Policy.Entity.ObjectOfInsuranceEntity;
import EDA.MeowMed.Policy.Entity.PolicyEntity;

import java.time.LocalDate;
import java.util.HashMap;

public class TemporaryStorage {

    private final HashMap<PolicyEntity, Long> policyData = new HashMap<>();

    private final HashMap<Long, CustomerEntity> customerData = new HashMap<>();

    public HashMap<PolicyEntity, Long> getPolicyData() {
        return policyData;
    }

    public HashMap<Long, CustomerEntity> getCustomerData() {
        return customerData;
    }
}
