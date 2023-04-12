package EDA.MeowMed.Messaging.EventObjects;

import EDA.MeowMed.Persistence.Entity.Policy;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public class PolicyCreatedEvent implements Serializable {
    @Serial
    private static final long serialVersionUID = 3L;

    private long id;

    private LocalDate startDate;

    private LocalDate endDate;

    private int coverage;

    private double premium;

    private ObjectOfInsurancePojo objectOfInsurancePojo;

    private CustomerPojo customer;

    public PolicyCreatedEvent(long id, LocalDate startDate, LocalDate endDate, int coverage, int premium, ObjectOfInsurancePojo objectOfInsurancePojo, CustomerPojo customer) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.coverage = coverage;
        this.premium = premium;
        this.objectOfInsurancePojo = objectOfInsurancePojo;
        this.customer = customer;
    }

    public PolicyCreatedEvent(Policy p) {
        this.id = p.getId();
        this.startDate = p.getStartDate();
        this.endDate = p.getEndDate();
        this.coverage = p.getCoverage();
        this.premium = p.getPremium();
        this.objectOfInsurancePojo = new ObjectOfInsurancePojo(p.getObjectOfInsurance());
        this.customer = new CustomerPojo(p.getCustomer());
    }

    public PolicyCreatedEvent() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getCoverage() {
        return coverage;
    }

    public void setCoverage(int coverage) {
        this.coverage = coverage;
    }

    public double getPremium() {
        return premium;
    }

    public void setPremium(double premium) {
        this.premium = premium;
    }

    public ObjectOfInsurancePojo getObjectOfInsurance() {
        return objectOfInsurancePojo;
    }

    public void setObjectOfInsurance(ObjectOfInsurancePojo objectOfInsurancePojo) {
        this.objectOfInsurancePojo = objectOfInsurancePojo;
    }

    public CustomerPojo getCustomer() {
        return this.customer;
    }

    public void setCustomer(CustomerPojo customer) {
        this.customer = customer;
    }
}
