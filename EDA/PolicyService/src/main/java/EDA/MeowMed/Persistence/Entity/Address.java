package EDA.MeowMed.Persistence.Entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name="Address")
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "postal_code", nullable = false)
    private int postalCode;

    public Address(int postalCode) {
        this.postalCode = postalCode;
    }

    public Address(events.customer.subclasses.Address a) {
        this.postalCode = a.getPostalCode();
    }

    public Address() {}

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
