package event.objects.billing;

import java.io.Serial;
import java.io.Serializable;

public class BillingEvent implements Serializable {
    @Serial
    private static final long serialVersionUID = 30L;

    private Long pid;

    public BillingEvent (Long pid) {
        this.pid = pid;
    }
    public Long getPid() {
        return pid;
    }
}
