package com.meowmed.rdapolicy.persistence.entity;

import java.time.LocalDate;

public class PolicyRequest {
    private LocalDate startDate;
    private LocalDate endDate;
    private int coverage;
    private ObjectOfInsuranceEntity objectOfInsurance;
    
    public PolicyRequest() {
    }
    public PolicyRequest(LocalDate startDate, LocalDate endDate, int coverage,ObjectOfInsuranceEntity objectOfInsurance) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.coverage = coverage;
        this.objectOfInsurance = objectOfInsurance;
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
    public ObjectOfInsuranceEntity getObjectOfInsurance() {
        return objectOfInsurance;
    }
    public void setObjectOfInsurance(ObjectOfInsuranceEntity objectOfInsurance) {
        this.objectOfInsurance = objectOfInsurance;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
        result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
        result = prime * result + coverage;
        result = prime * result + ((objectOfInsurance == null) ? 0 : objectOfInsurance.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PolicyRequest other = (PolicyRequest) obj;
        if (startDate == null) {
            if (other.startDate != null)
                return false;
        } else if (!startDate.equals(other.startDate))
            return false;
        if (endDate == null) {
            if (other.endDate != null)
                return false;
        } else if (!endDate.equals(other.endDate))
            return false;
        if (coverage != other.coverage)
            return false;
        if (objectOfInsurance == null) {
            if (other.objectOfInsurance != null)
                return false;
        } else if (!objectOfInsurance.equals(other.objectOfInsurance))
            return false;
        return true;
    }
    @Override
    public String toString() {
        return "PolicyRequest [startDate=" + startDate + ", endDate=" + endDate + ", coverage=" + coverage
                + ", objectOfInsurance=" + objectOfInsurance + "]";
    }

    
}
