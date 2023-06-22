package EDA.PolicyService.Rest;

import EDA.PolicyService.Persistence.Entity.Policy;

/**
 * Class that represents the data that is relevant to calculate a premium
 */
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
