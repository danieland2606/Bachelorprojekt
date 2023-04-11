package EDA.MeowMed.Messaging.EventObjects;

import EDA.MeowMed.Persistence.Entity.Address;

import java.io.Serial;
import java.io.Serializable;

/**
 * Object with address information of a customer
 * Can ben serialized and sent as event payload
 */
public class NoId_Address implements Serializable{
    @Serial
    private static final long serialVersionUID = 2L;
    private String city;
    private String street;
    private String postalCode;

    public NoId_Address() {

    }

    public NoId_Address(Address address) {
        this.city = address.getCity();
        this.street = address.getStreet();
        this.postalCode = address.getPostalCode();
    }

    public NoId_Address(String city, String street, String postalCode) {
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

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
