package events.policy;

import events.policy.subclasses.CustomerPojo;

import java.io.Serial;
import java.io.Serializable;

public class PolicyChangedEvent implements Serializable {
    @Serial
    private static final long serialVersionUID = 6L;

    public PolicyChangedEvent() {

    }

    public PolicyChangedEvent(long policyID, int oldCoverage, int newCoverage, double oldPremium, double newPremium, CustomerPojo customer) {
        this.policyID = policyID;
        this.oldCoverage = oldCoverage;
        this.newCoverage = newCoverage;
        this.oldPremium = oldPremium;
        this.newPremium = newPremium;
        this.customer = customer;
    }

    private long policyID;

    private int oldCoverage;
    private int newCoverage;

    private double oldPremium;

    private double newPremium;

    private CustomerPojo customer;

    public long getPolicyID() {
        return policyID;
    }

    public void setPolicyID(long policyID) {
        this.policyID = policyID;
    }

    public int getOldCoverage() {
        return oldCoverage;
    }

    public void setOldCoverage(int oldCoverage) {
        this.oldCoverage = oldCoverage;
    }

    public int getNewCoverage() {
        return newCoverage;
    }

    public void setNewCoverage(int newCoverage) {
        this.newCoverage = newCoverage;
    }

    public double getOldPremium() {
        return oldPremium;
    }

    public void setOldPremium(double oldPremium) {
        this.oldPremium = oldPremium;
    }

    public double getNewPremium() {
        return newPremium;
    }

    public void setNewPremium(double newPremium) {
        this.newPremium = newPremium;
    }

    public CustomerPojo getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerPojo customer) {
        this.customer = customer;
    }
}
