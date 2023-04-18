package EDA.MeowMed.Persistence.Entity;


import EDA.MeowMed.Messaging.EventObjects.CustomerCreatedEvent;
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

    @Column(name = "marital_status", nullable = false)
    private String maritalStatus;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "employment_status")
    private String employmentStatus;

    @OneToOne(optional = false)
    private Address address;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "e_mail", nullable = false)
    private String email;

    @Column(name = "bank_details", nullable = false)
    private String bankDetails;

    public Customer(Long id, String firstName, String lastName, String formOfAddress, String title, String maritalStatus, LocalDate dateOfBirth, String employmentStatus, Address address, String phoneNumber, String email, String bankDetails) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.formOfAddress = formOfAddress;
        this.title = title;
        this.maritalStatus = maritalStatus;
        this.dateOfBirth = dateOfBirth;
        this.employmentStatus = employmentStatus;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.bankDetails = bankDetails;
    }

    public Customer(CustomerCreatedEvent c) {
        this.id = c.getId();
        this.firstName = c.getFirstName();
        this.lastName = c.getLastName();
        this.formOfAddress = c.getFormOfAddress();
        this.title = c.getTitle();
        this.maritalStatus = c.getMaritalStatus();
        this.dateOfBirth = c.getDateOfBirth();
        this.employmentStatus = c.getEmploymentStatus();
        this.address = new Address(c.getAddress());
        this.phoneNumber = c.getPhoneNumber();
        this.email = c.getEmail();
        this.bankDetails = c.getBankDetails();
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

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmploymentStatus() {
        return employmentStatus;
    }

    public void setEmploymentStatus(String employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBankDetails() {
        return bankDetails;
    }

    public void setBankDetails(String bankDetails) {
        this.bankDetails = bankDetails;
    }

}
