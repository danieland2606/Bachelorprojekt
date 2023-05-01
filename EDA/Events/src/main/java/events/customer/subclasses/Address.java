package events.customer.subclasses;

import java.io.Serial;
import java.io.Serializable;

public class Address implements Serializable {
    @Serial
    private static final long serialVersionUID = 2L;
    private String city;
    private String street;
    private int postalCode;
    public Address() {

    }

    public Address(String city, String street, int postalCode) {
        this.city = city;
        this.street = street;
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public String toString(){
        return postalCode + " " + city + ", " + street;
    }
}