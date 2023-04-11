package EDA.MeowMed.REST.Objects;

import EDA.MeowMed.Persistence.Entity.Customer;

import java.time.LocalDate;

public class View_Customer {
    private Long id;
    private String firstName;
    private String lastName;
    private String formOfAddress;
    private String title;
    private String martialStatus;
    private LocalDate dateOfBirth;
    private String employmentStatus;
    private View_Address address;
    private String phoneNumber;
    private String email;
    private String bankDetails;

    public View_Customer() {
    }

    public View_Customer(Customer customer) {
        this.id = customer.getId();
        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
        this.formOfAddress = customer.getFormOfAddress();
        this.title = customer.getTitle();
        this.martialStatus = customer.getMaritalStatus();
        this.dateOfBirth = customer.getDateOfBirth();
        this.employmentStatus = customer.getEmploymentStatus();
        this.address = new View_Address(customer.getAddress());
        this.phoneNumber = customer.getPhoneNumber();
        this.email = customer.getEmail();
        this.bankDetails = customer.getBankDetails();
    }

    public View_Customer(Long id, String firstName, String lastName, String martialStatus, LocalDate dateOfBirth, String employmentStatus, View_Address address, String phoneNumber, String email, String bankDetails) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.formOfAddress = formOfAddress;
        this.title = title;
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

    public String getFormOfAddress() {return formOfAddress;}

    public void setFormOfAddress(String formOfAddress) {this.formOfAddress = formOfAddress;}

    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title;}

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

    public View_Address getAddress() {
        return address;
    }

    public void setAddress(View_Address address) {
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
