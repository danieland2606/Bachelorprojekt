package EDA.MeowMed.Messaging.EventObjects;

import EDA.MeowMed.Persistence.Entity.Customer;

import java.time.LocalDate;

public class CustomerCreatedEvent {
    private Long id;
    private String firstName;
    private String lastName;
    private String martialStatus;
    private LocalDate dateOfBirth;
    private String employmentStatus;
    private NoId_Address address;
    private String phoneNumber;
    private String email;
    private String bankDetails;

    public CustomerCreatedEvent() {
    }

    public CustomerCreatedEvent(Customer customer) {
        this.id = customer.getId();
        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
        this.martialStatus = customer.getMartialStatus();
        this.dateOfBirth = customer.getDateOfBirth();
        this.employmentStatus = customer.getEmploymentStatus();
        this.address = new NoId_Address(customer.getAddress());
        this.phoneNumber = customer.getPhoneNumber();
        this.email = customer.getEmail();
        this.bankDetails = customer.getBankDetails();
    }

    public CustomerCreatedEvent(Long id, String firstName, String lastName, String martialStatus, LocalDate dateOfBirth, String employmentStatus, NoId_Address address, String phoneNumber, String email, String bankDetails) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.martialStatus = martialStatus;
        this.dateOfBirth = dateOfBirth;
        this.employmentStatus = employmentStatus;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.bankDetails = bankDetails;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getMartialStatus() {
        return martialStatus;
    }

    public void setMartialStatus(String martialStatus) {
        this.martialStatus = martialStatus;
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

    public NoId_Address getAddress() {
        return address;
    }

    public void setAddress(NoId_Address address) {
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
