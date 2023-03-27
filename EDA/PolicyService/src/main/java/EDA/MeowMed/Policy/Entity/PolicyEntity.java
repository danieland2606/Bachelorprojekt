package EDA.MeowMed.Policy.Entity;

import java.io.Serializable;
import java.time.LocalDate;

public class PolicyEntity implements Serializable {
    private long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private int coverage;

    private double premium;

    private ObjectOfInsuranceEntity objectOfInsurance;

    public PolicyEntity(long id, LocalDate startDate, LocalDate endDate, int coverage, int premium, ObjectOfInsuranceEntity objectOfInsurance) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.coverage = coverage;
        this.premium = premium;
        this.objectOfInsurance = objectOfInsurance;
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

    public ObjectOfInsuranceEntity getObjectOfInsurance() {
        return objectOfInsurance;
    }

    public void setObjectOfInsurance(ObjectOfInsuranceEntity objectOfInsurance) {
        this.objectOfInsurance = objectOfInsurance;
    }
}
