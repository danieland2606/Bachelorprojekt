package com.meowmed.rdacustomer.entity;

import java.time.LocalDate;
import java.util.Objects;

public class MailCustomerEntity {

    private long id;
    private String firstName;
    private String lastName;
    private String title;
    private String formOfAddress;
    private String maritalStatus;
    private LocalDate dateOfBirth;
    private String employmentStatus;
    private String city;
    private String street;
    private String postalCode;
    private String phoneNumber;
    private String email;
    private String bankDetails;
    private boolean dogOwner;
    public MailCustomerEntity() {
    }


    public MailCustomerEntity(long id,String firstName, String lastName, String title, String formOfAddress,
            String maritalStatus, LocalDate dateOfBirth, String employmentStatus, String city, String street,
            String postalCode, String phoneNumber, String email, String bankDetails, boolean dogOwner) {
        this.id= id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
        this.formOfAddress = formOfAddress;
        this.maritalStatus = maritalStatus;
        this.dateOfBirth = dateOfBirth;
        this.employmentStatus = employmentStatus;
        this.city = city;
        this.street = street;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.bankDetails = bankDetails;
        this.dogOwner = dogOwner;
    }


    public MailCustomerEntity(long id, CustomerRequest cEntity){
        this.id = id;
        this.firstName = cEntity.getFirstName();
        this.lastName = cEntity.getLastName();
        this.title = cEntity.getTitle();
        this.formOfAddress = cEntity.getFormOfAddress();
        this.maritalStatus = cEntity.getMaritalStatus();
        this.dateOfBirth = cEntity.getDateOfBirth();
        this.employmentStatus = cEntity.getEmploymentStatus();
        this.city = cEntity.getAddress().getCity();
        this.street = cEntity.getAddress().getStreet();
        this.postalCode = cEntity.getAddress().getPostalCode();
        this.phoneNumber = cEntity.getPhoneNumber();
        this.email = cEntity.getEmail();
        this.bankDetails = cEntity.getBankDetails();
        this.dogOwner = cEntity.isDogOwner();
    }

    public long getId() {return id;}
    public void setId(long id) {this.id = id;}
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
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public String getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
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
    public boolean isDogOwner() { return dogOwner; }
    public void setDogOwner(boolean dogOwner) { this.dogOwner = dogOwner; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MailCustomerEntity that)) return false;
        return postalCode == that.postalCode && dogOwner == that.dogOwner && firstName.equals(that.firstName) && lastName.equals(that.lastName) && title.equals(that.title) && formOfAddress.equals(that.formOfAddress) && maritalStatus.equals(that.maritalStatus) && dateOfBirth.equals(that.dateOfBirth) && employmentStatus.equals(that.employmentStatus) && city.equals(that.city) && street.equals(that.street) && phoneNumber.equals(that.phoneNumber) && email.equals(that.email) && bankDetails.equals(that.bankDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, title, formOfAddress, maritalStatus, dateOfBirth, employmentStatus, city, street, postalCode, phoneNumber, email, bankDetails, dogOwner);
    }

    @Override
    public String toString() {
        return "MailCustomerEntity{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", title='" + title + '\'' +
                ", formOfAddress='" + formOfAddress + '\'' +
                ", maritalStatus='" + maritalStatus + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", employmentStatus='" + employmentStatus + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", postalCode=" + postalCode +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", bankDetails='" + bankDetails + '\'' +
                ", dogOwner=" + dogOwner +
                '}';
    }
}

