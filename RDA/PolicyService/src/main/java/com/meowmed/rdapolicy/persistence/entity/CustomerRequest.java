package com.meowmed.rdapolicy.persistence.entity;

import java.time.LocalDate;
import java.util.Objects;

public class CustomerRequest {

    private String firstName;
    private String lastName;
    private String title;
    private String formOfAddress;
    private String maritalStatus;
    private LocalDate dateOfBirth;
    private String employmentStatus;
    private AddressEntity address;
    private String phoneNumber;
    private String email;
    private String bankDetails;
    private boolean dogOwner;
    public CustomerRequest() {
    }
    public CustomerRequest(String firstName, String lastName, String title, String formOfAddress, String maritalStatus,
                           LocalDate dateOfBirth, String employmentStatus, AddressEntity address, String phoneNumber, String email,
                           String bankDetails, boolean dogOwner) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
        this.formOfAddress = formOfAddress;
        this.maritalStatus = maritalStatus;
        this.dateOfBirth = dateOfBirth;
        this.employmentStatus = employmentStatus;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.bankDetails = bankDetails;
        this.dogOwner= dogOwner;
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
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getFormOfAddress() {
        return formOfAddress;
    }
    public void setFormOfAddress(String formOfAddress) {
        this.formOfAddress = formOfAddress;
    }
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
    public AddressEntity getAddress() {
        return address;
    }
    public void setAddress(AddressEntity address) {
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
    public boolean isdogOwner() {return dogOwner;}
    public void setdogOwner(boolean dogOwner) {this.dogOwner = dogOwner;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerRequest that)) return false;
        return dogOwner == that.dogOwner && firstName.equals(that.firstName) && lastName.equals(that.lastName) && title.equals(that.title) && formOfAddress.equals(that.formOfAddress) && maritalStatus.equals(that.maritalStatus) && dateOfBirth.equals(that.dateOfBirth) && employmentStatus.equals(that.employmentStatus) && address.equals(that.address) && phoneNumber.equals(that.phoneNumber) && email.equals(that.email) && bankDetails.equals(that.bankDetails);
    }
    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, title, formOfAddress, maritalStatus, dateOfBirth, employmentStatus, address, phoneNumber, email, bankDetails, dogOwner);
    }
    @Override
    public String toString() {
        return "CustomerRequest{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", title='" + title + '\'' +
                ", formOfAddress='" + formOfAddress + '\'' +
                ", maritalStatus='" + maritalStatus + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", employmentStatus='" + employmentStatus + '\'' +
                ", address=" + address +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", bankDetails='" + bankDetails + '\'' +
                ", dogOwner=" + dogOwner +
                '}';
    }
}
