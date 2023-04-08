package EDA.MeowMed.REST.Objects;

import EDA.MeowMed.Persistence.Entity.Address;

public class View_Address {
    private String city;
    private String street;
    private String postalCode;

    public View_Address() {
    }

    public View_Address(Address address) {
        this.city = address.getCity();
        this.street = address.getStreet();
        this.postalCode = address.getPostalCode();
    }

    public View_Address(String city, String street, String postalCode) {
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
