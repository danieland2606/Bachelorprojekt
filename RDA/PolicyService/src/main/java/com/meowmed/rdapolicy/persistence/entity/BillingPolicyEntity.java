package com.meowmed.rdapolicy.persistence.entity;

import java.time.LocalDate;

public class BillingPolicyEntity {
    private LocalDate dueDate;
    private double monthlyCost;
    private String bankDetails;
    private long cid;
    private String firstName;
    private String lastName;
    private long pid;
    private LocalDate startDate;
    private String billingReason;
    
    public BillingPolicyEntity() {
    }

    public BillingPolicyEntity(LocalDate dueDate, double monthlyCost, String bankDetails, long cid, String firstName,
            String lastName, long pid, LocalDate startDate, String billingReason) {
        this.dueDate = dueDate;
        this.monthlyCost = monthlyCost;
        this.bankDetails = bankDetails;
        this.cid = cid;
        this.firstName = firstName;
        this.lastName = lastName;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    @Override
    public String toString() {
        return "BillingPolicyEntity [dueDate=" + dueDate + ", monthlyCost=" + monthlyCost + ", bankDetails="
                + bankDetails + ", cid=" + cid + ", firstName=" + firstName + ", lastName=" + lastName + ", pid=" + pid
                + ", startDate=" + startDate + ", billingReason=" + billingReason + "]";
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
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        result = prime * result + (int) (pid ^ (pid >>> 32));
        result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
        result = prime * result + ((billingReason == null) ? 0 : billingReason.hashCode());
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
        BillingPolicyEntity other = (BillingPolicyEntity) obj;
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
        if (firstName == null) {
            if (other.firstName != null)
                return false;
        } else if (!firstName.equals(other.firstName))
            return false;
        if (lastName == null) {
            if (other.lastName != null)
                return false;
        } else if (!lastName.equals(other.lastName))
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
        return true;
    }

    

}
