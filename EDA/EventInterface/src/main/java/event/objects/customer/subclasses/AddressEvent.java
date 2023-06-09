package event.objects.customer.subclasses;

import java.io.Serial;
import java.io.Serializable;

public class AddressEvent implements Serializable {
    @Serial
    private static final long serialVersionUID = 11L;
    private String city;
    private String street;
    private String postalCode;

    public AddressEvent() {

    }

    public AddressEvent(String city, String street, String postalCode) {
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