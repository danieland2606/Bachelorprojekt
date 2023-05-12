package events.policy;

import events.policy.subclasses.PolicyPojo;

import java.io.Serial;
import java.io.Serializable;

public class PolicyChangedEvent implements Serializable {
    @Serial
    private static final long serialVersionUID = 6L;

    private PolicyPojo oldPolicy;

    private PolicyPojo newPolicy;

    public PolicyChangedEvent(PolicyPojo oldPolicy, PolicyPojo newPolicy) {
        this.oldPolicy = oldPolicy;
        this.newPolicy = newPolicy;
    }

    public PolicyChangedEvent() {

    }

    public PolicyPojo getOldPolicy() {
        return oldPolicy;
    }

    public void setOldPolicy(PolicyPojo oldPolicy) {
        this.oldPolicy = oldPolicy;
    }

    public PolicyPojo getNewPolicy() {
        return newPolicy;
    }

    public void setNewPolicy(PolicyPojo newPolicy) {
        this.newPolicy = newPolicy;
    }
}
