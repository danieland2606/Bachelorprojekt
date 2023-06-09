package EDA.NotificationService.Persistence.Entity;

import event.objects.customer.CustomerEvent;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "Customer")
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "customer_id", unique = true, nullable = false)
    private Long cid;

    @Column(name = "form_Of_Address", nullable = false)
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

    @Column(name = "employment_status", nullable = false)
    private String employmentStatus;

    @Column(name = "dog_owner", nullable = false)
    private boolean dogOwner;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "e_mail", nullable = false)
    private String email;

    @Column(name = "bank_details", nullable = false)
    private String bankDetails;

    public Customer() {
    }

    public Customer(Long cid, String firstName, String lastName, String formOfAddress, String title, String maritalStatus, LocalDate dateOfBirth, String employmentStatus, boolean dogOwner, Address address, String phoneNumber, String email, String bankDetails) {
        this.cid = cid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.formOfAddress = formOfAddress;
        this.title = title;
        this.maritalStatus = maritalStatus;
        this.dateOfBirth = dateOfBirth;
        this.employmentStatus = employmentStatus;
        this.dogOwner = dogOwner;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.bankDetails = bankDetails;
    }

    public Customer(CustomerEvent customerEvent) {
        this.cid = customerEvent.getCid();
        this.firstName = customerEvent.getFirstName();
        this.lastName = customerEvent.getLastName();
        this.formOfAddress = customerEvent.getFormOfAddress();
        this.title = customerEvent.getTitle();
        this.maritalStatus = customerEvent.getMaritalStatus();
        this.dateOfBirth = customerEvent.getDateOfBirth();
        this.employmentStatus = customerEvent.getEmploymentStatus();
        this.dogOwner = customerEvent.isDogOwner();
        this.address = new Address(customerEvent.getAddress());
        this.phoneNumber = customerEvent.getPhoneNumber();
        this.email = customerEvent.getEmail();
        this.bankDetails = customerEvent.getBankDetails();
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
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

    public String getFormOfAddress() {
        return formOfAddress;
    }

    public void setFormOfAddress(String formOfAddress) {
        this.formOfAddress = formOfAddress;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String martialStatus) {
        this.maritalStatus = martialStatus;
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

    public boolean getDogOwner() {
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
