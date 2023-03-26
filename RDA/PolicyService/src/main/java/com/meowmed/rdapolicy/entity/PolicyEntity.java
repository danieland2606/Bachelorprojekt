package com.meowmed.rdapolicy.entity;


import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;



@Entity
@Table(name="Contract")
@JsonFilter("policyFilter")
public class PolicyEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="Policy_ID")
    @JsonProperty("id")
    private long id;

    @JsonProperty("c_id")
    private long c_id;
    
    @JsonProperty("startDate")
    private LocalDate startDate;
    
    @JsonProperty("endDate")
    private LocalDate endDate;

    @JsonProperty("coverage")
    private int coverage;

    @JsonProperty("premium")
    private int premium;

    @OneToOne
    @JsonProperty("objectOfInsurance")
    @JsonFilter("objectOfInsuranceFilter")
    private ObjectOfInsuranceEntity objectOfInsurance;
    
    public PolicyEntity() {
    }

    public PolicyEntity(long id, long c_id, LocalDate startDate, LocalDate endDate, int coverage, int premium,
            ObjectOfInsuranceEntity objectOfInsurance) {
        this.id = id;
        this.c_id = c_id;
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

    public int getPremium() {
        return premium;
    }

    public void setPremium(int premium) {
        this.premium = premium;
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
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + (int) (c_id ^ (c_id >>> 32));
        result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
        result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
        result = prime * result + coverage;
        result = prime * result + premium;
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
        if (premium != other.premium)
            return false;
        if (objectOfInsurance == null) {
            if (other.objectOfInsurance != null)
                return false;
        } else if (!objectOfInsurance.equals(other.objectOfInsurance))
            return false;
        return true;
    }
    
    
    
}
