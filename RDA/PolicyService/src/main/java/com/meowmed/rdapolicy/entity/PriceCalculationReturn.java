package com.meowmed.rdapolicy.entity;

public class PriceCalculationReturn {
    double premium;

    public PriceCalculationReturn() {
    }

    public PriceCalculationReturn(double premium) {
        this.premium = premium;
    }

    public double getPremium() {
        return premium;
    }

    public void setPremium(double premium) {
        this.premium = premium;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(premium);
        result = prime * result + (int) (temp ^ (temp >>> 32));
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
        PriceCalculationReturn other = (PriceCalculationReturn) obj;
        if (Double.doubleToLongBits(premium) != Double.doubleToLongBits(other.premium))
            return false;
        return true;
    }

    
}
