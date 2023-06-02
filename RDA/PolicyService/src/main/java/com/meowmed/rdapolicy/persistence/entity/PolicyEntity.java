package com.meowmed.rdapolicy.persistence.entity;


import java.time.LocalDate;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.annotation.JsonFilter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


@Repository
@Entity
@Table(name="Contract")
@JsonFilter("policyFilter")
public class PolicyEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="Policy_ID")
    private long id;

    @Column(name="c_id")
    private long cid;
    
    private LocalDate startDate;
    
    private LocalDate endDate;

    private int coverage;

    private double premium;

    @OneToOne
    private ObjectOfInsuranceEntity objectOfInsurance;

    private boolean active;

    private LocalDate dueDate;
    
    public PolicyEntity() {
    }

    public PolicyEntity(long c_id, LocalDate startDate, LocalDate endDate, int coverage, double premium,
            ObjectOfInsuranceEntity objectOfInsurance, LocalDate dueDate) {
        this.cid = c_id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.coverage = coverage;
        this.premium = premium;
        this.objectOfInsurance = objectOfInsurance;
        this.active = true;
        this.dueDate = dueDate;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public long getCid() {
        return cid;
    }

    public void setCid(long cid) {
        this.cid = cid;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public boolean equalsPolicyRequest(long c_id, PolicyRequest other){
        if (cid != c_id)
            return false;
        if (startDate == null) {
            if (other.getStartDate() != null)
                return false;
        } else if (!startDate.equals(other.getStartDate()))
            return false;
        if (endDate == null) {
            if (other.getEndDate() != null)
                return false;
        } else if (!endDate.equals(other.getEndDate()))
            return false;
        if (coverage != other.getCoverage())
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "PolicyEntity [id=" + id + ", cid=" + cid + ", startDate=" + startDate + ", endDate=" + endDate
                + ", coverage=" + coverage + ", premium=" + premium + ", objectOfInsurance=" + objectOfInsurance
                + ", active=" + active + ", dueDate=" + dueDate + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (cid ^ (cid >>> 32));
        result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
        result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
        result = prime * result + coverage;
        long temp;
        temp = Double.doubleToLongBits(premium);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((objectOfInsurance == null) ? 0 : objectOfInsurance.hashCode());
        result = prime * result + (active ? 1231 : 1237);
        result = prime * result + ((dueDate == null) ? 0 : dueDate.hashCode());
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
        if (cid != other.cid)
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
        if (Double.doubleToLongBits(premium) != Double.doubleToLongBits(other.premium))
            return false;
        if (objectOfInsurance == null) {
            if (other.objectOfInsurance != null)
                return false;
        } else if (!objectOfInsurance.equals(other.objectOfInsurance))
            return false;
        if (active != other.active)
            return false;
        if (dueDate == null) {
            if (other.dueDate != null)
                return false;
        } else if (!dueDate.equals(other.dueDate))
            return false;
        return true;
    }
    
    
    
    
}
