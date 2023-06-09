package EDA.PolicyService.Rest;

import EDA.PolicyService.Persistence.Entity.Policy;

public class PremiumCalculationData {
    private long customerId;

    private Policy policy;

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public Policy getPolicy() {
        return policy;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
    }
}
