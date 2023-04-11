package EDA.MeowMed.Policy.View;

import jakarta.persistence.*;

import java.time.LocalDate;


public class PolicyOverview {


    private  long id;
    private  LocalDate startDate;
    private  LocalDate endDate;
    private  int coverage;
    private  ObjectOfInsuranceOverview objectOfInsurance;



    //Wichtig für die Projection von getPolicyByCustomerIdAndPolicyId
    public PolicyOverview(long id, LocalDate startDate, LocalDate endDate, int coverage) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.coverage = coverage;
        //this.objectOfInsurance = objectOfInsurance;
    }


    public PolicyOverview(long id, LocalDate startDate, LocalDate endDate, int coverage, ObjectOfInsuranceOverview objectOfInsurance) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.coverage = coverage;
        this.objectOfInsurance = objectOfInsurance;
    }

    public PolicyOverview() {

    }

    public long getId() {
        return id;
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

    public ObjectOfInsuranceOverview getObjectOfInsurance() {
        return objectOfInsurance;
    }

    public void setObjectOfInsurance(ObjectOfInsuranceOverview objectOfInsurance) {
        this.objectOfInsurance = objectOfInsurance;
    }
}
