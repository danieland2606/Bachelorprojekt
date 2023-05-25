package EDA.MeowMed.Persistence.Entity;


import event.objects.customer.CustomerCreatedEvent;
import event.objects.policy.subclasses.CustomerPojo;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name="Customer")
public class Customer implements Serializable {


    //TODO Date of birth of Customer hinzufügen
    //TODO DogOwner hinzufügen
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

    @Column(name = "dog_owner")
    private boolean dogOwner;

    @OneToOne(optional = false)
    private Address address;

    @Column(name = "e_mail", nullable = false)
    private String email;

    @Column(name = "employment_status", nullable = false)
    private String employmentStatus;


    public Customer(Long id, String firstName, String lastName, String formOfAddress, String title, boolean dogOwner, Address address, String email, String employmentStatus) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.formOfAddress = formOfAddress;
        this.title = title;
        this.dogOwner = dogOwner;
        this.address = address;
        this.email = email;
        this.employmentStatus = employmentStatus;
    }

    public Customer(CustomerCreatedEvent c) {
        this.id = c.getCid();
        this.firstName = c.getFirstName();
        this.lastName = c.getLastName();
        this.formOfAddress = c.getFormOfAddress();
        this.title = c.getTitle();
        this.dogOwner = c.getDogOwner();
        this.address = new Address(c.getAddress());
        this.email = c.getEmail();
        this.employmentStatus = c.getEmploymentStatus();
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

    public boolean isDogOwner() {
        return dogOwner;
    }

    public void setDogOwner(boolean dogOwner) {
        this.dogOwner = dogOwner;
    }

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

    public String getEmploymentStatus() {
        return this.employmentStatus;
    }

    public void setEmploymentStatus(String employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

    public CustomerPojo toPojo() {
        return new CustomerPojo(
                id,
                firstName,
                lastName,
                formOfAddress,
                email
        );
    }
}
