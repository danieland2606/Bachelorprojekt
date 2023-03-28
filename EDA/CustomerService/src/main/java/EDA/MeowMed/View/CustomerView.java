package EDA.MeowMed.View;

import java.time.LocalDate;

public class CustomerView {
    private String firstName;
    private String lastName;
    private String martialStatus;
    private LocalDate dateOfBirth;
    private String employmentStatus;
    private AddressWithoutId address;
    private String phoneNumber;
    private String email;
    private String bankDetails;

    public CustomerView(String firstName, String lastName, String martialStatus, LocalDate dateOfBirth, String employmentStatus, AddressWithoutId address, String phoneNumber, String email, String bankDetails) {
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

    public CustomerView() {
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

    public AddressWithoutId getAddress() {
        return address;
    }

    public void setAddress(AddressWithoutId address) {
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
