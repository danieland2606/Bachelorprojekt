package EDA.MeowMed.Policy.View;

import java.time.LocalDate;

public class PolicyView {
    private long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private int coverage;

    private int premium;

    private ObjectOfInsuranceView objectOfInsurance;

    public PolicyView(long id, LocalDate startDate, LocalDate endDate, int coverage, int premium, ObjectOfInsuranceView objectOfInsurance) {
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

    public int getPremium() {
        return premium;
    }

    public void setPremium(int premium) {
        this.premium = premium;
    }

    public ObjectOfInsuranceView getObjectOfInsurance() {
        return objectOfInsurance;
    }

    public void setObjectOfInsurance(ObjectOfInsuranceView objectOfInsurance) {
        this.objectOfInsurance = objectOfInsurance;
    }
}
