package events.customer;

import events.customer.subclasses.CustomerData;

import java.io.Serial;
import java.io.Serializable;


/**
 * event
 */
public class CustomerChangedEvent implements Serializable {
    @Serial
    private static final long serialVersionUID = 7L;

    private CustomerData oldCustomer;
    private CustomerData newCustomer;

    public CustomerChangedEvent(CustomerData oldCustomer, CustomerData newCustomer) {
        this.oldCustomer = oldCustomer;
        this.newCustomer = newCustomer;
    }

    public CustomerData getOldCustomer() {
        return oldCustomer;
    }

    public void setOldCustomer(CustomerData oldCustomer) {
        this.oldCustomer = oldCustomer;
    }

    public CustomerData getNewCustomer() {
        return newCustomer;
    }

    public void setNewCustomer(CustomerData newCustomer) {
        this.newCustomer = newCustomer;
    }
}
