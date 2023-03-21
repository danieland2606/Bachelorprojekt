package com.meowmed.policy.entity;

import java.time.LocalDate;

public class PolicyEntity {
    private long id;
    private long c_id;
    private LocalDate startDate;
    private LocalDate endDate;
    private int coverage;
    private ObjectOfInsuranceEntity objectOfInsurance;
    public PolicyEntity() {
    }
    public PolicyEntity(long id, long c_id, LocalDate startDate, LocalDate endDate, int coverage,
            ObjectOfInsuranceEntity objectOfInsurance) {
        this.id = id;
        this.c_id = c_id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.coverage = coverage;
        this.objectOfInsurance = objectOfInsurance;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getC_id() {
        return c_id;
    }
    public void setC_id(long c_id) {
        this.c_id = c_id;
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
    public String toString() {
        return "PolicyEntity [id=" + id + ", c_id=" + c_id + ", startDate=" + startDate + ", endDate=" + endDate
                + ", coverage=" + coverage + ", objectOfInsurance=" + objectOfInsurance + "]";
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + (int) (c_id ^ (c_id >>> 32));
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
        PolicyEntity other = (PolicyEntity) obj;
        if (id != other.id)
            return false;
        if (c_id != other.c_id)
            return false;
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
}
