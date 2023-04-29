package EDA.MeowMed.Persistence.Entity;


import events.customer.CustomerCreatedEvent;
import events.policy.subclasses.CustomerPojo;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name="Customer")
public class Customer implements Serializable {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "form_Of_Address")
    private String formOfAddress;

    @Column(name = "title")
    private String title;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @OneToOne(optional = false)
    private Address address;

    @Column(name = "e_mail", nullable = false)
    private String email;

    public Customer(Long id, String firstName, String lastName, String formOfAddress, String title, Address address, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.formOfAddress = formOfAddress;
        this.title = title;
        this.address = address;
        this.email = email;
    }

    public Customer(CustomerCreatedEvent c) {
        this.id = c.getId();
        this.firstName = c.getFirstName();
        this.lastName = c.getLastName();
        this.formOfAddress = c.getFormOfAddress();
        this.title = c.getTitle();
        this.address = new Address(c.getAddress());
        this.email = c.getEmail();
    }

    public Customer() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFormOfAddress() {return formOfAddress;}

    public void setFormOfAddress(String formOfAddress) {this.formOfAddress = formOfAddress;}

    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title;}

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CustomerPojo toPojo() {
        return new CustomerPojo(
                id,
                firstName,
                lastName,
                formOfAddress,
                address.toCustomerAddress(),
                email
        );
    }
}
