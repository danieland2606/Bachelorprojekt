package com.meowmed.rdapolicy.persistence.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PriceCalculationEntity {
    
    private long customerId;
    private PolicyRequest policy;
    
    public PriceCalculationEntity() {
    }

    public PriceCalculationEntity(long customerId, PolicyRequest policy) {
        this.customerId = customerId;
        this.policy = policy;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public PolicyRequest getPolicy() {
        return policy;
    }

    public void setPolicy(PolicyRequest policy) {
        this.policy = policy;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (customerId ^ (customerId >>> 32));
        result = prime * result + ((policy == null) ? 0 : policy.hashCode());
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
        PriceCalculationEntity other = (PriceCalculationEntity) obj;
        if (customerId != other.customerId)
            return false;
        if (policy == null) {
            if (other.policy != null)
                return false;
        } else if (!policy.equals(other.policy))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "PriceCalculationEntity [customerId=" + customerId + ", policy=" + policy + "]";
    }

    
}
