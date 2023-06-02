package com.meowmed.rdabilling.entity;

import java.time.LocalDate;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.annotation.JsonFilter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Repository
@Entity
@JsonFilter("billingFilter")
public class BillingEntity {
    private LocalDate dueDate;
    private double amount;
    private String bankDetails;
    private long cid;
    private String name;
    private long pid;
    private LocalDate startDate;
    private String details;
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    
    public BillingEntity() {
    }

    public BillingEntity(LocalDate dueDate, double amount, String bankDetails, long cid, String name, long pid, LocalDate startDate, String details) {
        this.dueDate = dueDate;
        this.amount = amount;
        this.bankDetails = bankDetails;
        this.cid = cid;
        this.name = name;
        this.pid = pid;
        this.startDate = startDate;
        this.details = details;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public double getamount() {
        return amount;
    }

    public void setamount(double amount) {
        this.amount = amount;
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

    public String getdetails() {
        return details;
    }

    public void setdetails(String details) {
        this.details = details;
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
        temp = Double.doubleToLongBits(amount);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((bankDetails == null) ? 0 : bankDetails.hashCode());
        result = prime * result + (int) (cid ^ (cid >>> 32));
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + (int) (pid ^ (pid >>> 32));
        result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
        result = prime * result + ((details == null) ? 0 : details.hashCode());
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
        if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
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
        if (details == null) {
            if (other.details != null)
                return false;
        } else if (!details.equals(other.details))
            return false;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "BillingEntity [dueDate=" + dueDate + ", amount=" + amount + ", bankDetails=" + bankDetails
                + ", cid=" + cid + ", name=" + name + ", pid=" + pid
                + ", startDate=" + startDate + ", details=" + details + ", id=" + id + "]";
    }

    
    
}
