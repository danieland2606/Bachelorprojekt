package EDA.PolicyService.Persistence.Entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "Address")
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    public Address(String postalCode) {
        this.postalCode = postalCode;
    }

    public Address(event.objects.customer.subclasses.AddressEvent a) {
        this.postalCode = a.getPostalCode();
    }

    public Address() {
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
}
