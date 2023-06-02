package com.meowmed.rdabilling.entity;

import java.time.LocalDate;

import org.springframework.stereotype.Repository;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Repository
@Entity
public class BillingEntity {
    private LocalDate dueDate;
    private double monthlyCost;
    private String bankDetails;
    private long cid;
    private String name;
    private long pid;
    private LocalDate startDate;
    private String billingReason;
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    
    public BillingEntity() {
    }

    public BillingEntity(LocalDate dueDate, double monthlyCost, String bankDetails, long cid, String name, long pid, LocalDate startDate, String billingReason) {
        this.dueDate = dueDate;
        this.monthlyCost = monthlyCost;
        this.bankDetails = bankDetails;
        this.cid = cid;
        this.name = name;
        this.pid = pid;
        this.startDate = startDate;
        this.billingReason = billingReason;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public double getMonthlyCost() {
        return monthlyCost;
    }

    public void setMonthlyCost(double monthlyCost) {
        this.monthlyCost = monthlyCost;
    }

    public String getBankDetails() {
        return bankDetails;
    }

    public void setBankDetails(String bankDetails) {
        this.bankDetails = bankDetails;
    }

    public long getCid() {
        return cid;
    }

    public void setCid(long cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String firstName) {
        this.name = firstName;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getBillingReason() {
        return billingReason;
    }

    public void setBillingReason(String billingReason) {
        this.billingReason = billingReason;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dueDate == null) ? 0 : dueDate.hashCode());
        long temp;
        temp = Double.doubleToLongBits(monthlyCost);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((bankDetails == null) ? 0 : bankDetails.hashCode());
        result = prime * result + (int) (cid ^ (cid >>> 32));
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + (int) (pid ^ (pid >>> 32));
        result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
        result = prime * result + ((billingReason == null) ? 0 : billingReason.hashCode());
        result = prime * result + (int) (id ^ (id >>> 32));
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
        BillingEntity other = (BillingEntity) obj;
        if (dueDate == null) {
            if (other.dueDate != null)
                return false;
        } else if (!dueDate.equals(other.dueDate))
            return false;
        if (Double.doubleToLongBits(monthlyCost) != Double.doubleToLongBits(other.monthlyCost))
            return false;
        if (bankDetails == null) {
            if (other.bankDetails != null)
                return false;
        } else if (!bankDetails.equals(other.bankDetails))
            return false;
        if (cid != other.cid)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (pid != other.pid)
            return false;
        if (startDate == null) {
            if (other.startDate != null)
                return false;
        } else if (!startDate.equals(other.startDate))
            return false;
        if (billingReason == null) {
            if (other.billingReason != null)
                return false;
        } else if (!billingReason.equals(other.billingReason))
            return false;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "BillingEntity [dueDate=" + dueDate + ", monthlyCost=" + monthlyCost + ", bankDetails=" + bankDetails
                + ", cid=" + cid + ", name=" + name + ", pid=" + pid
                + ", startDate=" + startDate + ", billingReason=" + billingReason + ", id=" + id + "]";
    }

    
    
}
