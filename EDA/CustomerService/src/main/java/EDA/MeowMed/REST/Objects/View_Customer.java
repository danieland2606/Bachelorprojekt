package EDA.MeowMed.REST.Objects;

import EDA.MeowMed.Persistence.Entity.Customer;

import java.time.LocalDate;

public class View_Customer {
    private String firstName;
    private String lastName;
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
        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
        this.martialStatus = customer.getMartialStatus();
        this.dateOfBirth = customer.getDateOfBirth();
        this.employmentStatus = customer.getEmploymentStatus();
        this.address = new View_Address(customer.getAddress());
        this.phoneNumber = customer.getPhoneNumber();
        this.email = customer.getEmail();
        this.bankDetails = customer.getBankDetails();
    }

    public View_Customer(String firstName, String lastName, String martialStatus, LocalDate dateOfBirth, String employmentStatus, View_Address address, String phoneNumber, String email, String bankDetails) {
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