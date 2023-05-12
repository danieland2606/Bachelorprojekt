package events.policy;

import events.policy.subclasses.PolicyPojo;

import java.io.Serial;
import java.io.Serializable;

public class PolicyCreatedEvent implements Serializable {
    @Serial
    private static final long serialVersionUID = 3L;

    private PolicyPojo policy;

    public PolicyCreatedEvent(PolicyPojo policy) {
        this.policy = policy;
    }

    public PolicyPojo getPolicy() {
        return this.policy;
    }

    public void setPolicy(PolicyPojo policy) {
        this.policy = policy;
    }
}
