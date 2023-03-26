package com.meowmed.rdapolicy.entity;

public class PolicyPostResponse {
    private long id;

    public PolicyPostResponse() {
    }

    public PolicyPostResponse(long id) {
        this.id = id;
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
        PolicyPostResponse other = (PolicyPostResponse) obj;
        if (id != other.id)
            return false;
        return true;
    }
    
}
