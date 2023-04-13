package EDA.MeowMed.Persistence.Entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

@Entity
@Table(name = "Address")
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "a_id", unique = true, nullable = false)
    private Long id;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    public Address(Long id, String city, String street, String postalCode) {
        this.id = id;
        this.city = city;
        this.street = street;
        this.postalCode = postalCode;
    }

    public Address() {
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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    /**
     * Todo: needs comment
     * No id cus id is internal
     *
     * @return
     */
    public Map<String, Object> toMap() {
        Map<String, Object> address = new LinkedHashMap<>();
        address.put("city", city);
        address.put("street", street);
        address.put("postalCode", postalCode);
        return address;
    }
}
