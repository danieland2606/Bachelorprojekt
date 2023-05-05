package com.meowmed.rdacustomer.entity;

import com.fasterxml.jackson.annotation.JsonFilter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name="Customer")
@JsonFilter("customerFilter")
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="Customer_ID")
    private long id;
    private String firstName;
    private String lastName;
    private String title;
    private String formOfAdress;
    private String maritalStatus;
    private LocalDate dateOfBirth;
    private String employmentStatus;
    @OneToOne
    private AddressEntity address;
    private String phoneNumber;
    private String email;
    private String bankDetails;
    private boolean dogOwner;
    public CustomerEntity() {
    }

    public CustomerEntity(String firstName, String lastName, String title, String formOfAdress, String maritalStatus,
                          LocalDate dateOfBirth, String employmentStatus, AddressEntity address, String phoneNumber, String email,
                          String bankDetails, boolean dogOwner) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
        this.formOfAdress = formOfAdress;
        this.maritalStatus = maritalStatus;
        this.dateOfBirth = dateOfBirth;
        this.employmentStatus = employmentStatus;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.bankDetails = bankDetails;
        this.dogOwner = dogOwner;
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
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getFormOfAdress() {
        return formOfAdress;
    }
    public void setFormOfAdress(String formOfAdress) {
        this.formOfAdress = formOfAdress;
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
    public boolean isDogOwner() { return dogOwner;}
    public void setDogOwner(boolean hasDog) { this.dogOwner = hasDog;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerEntity that)) return false;
        return id == that.id && dogOwner == that.dogOwner && firstName.equals(that.firstName) && lastName.equals(that.lastName) && title.equals(that.title) && formOfAdress.equals(that.formOfAdress) && maritalStatus.equals(that.maritalStatus) && dateOfBirth.equals(that.dateOfBirth) && employmentStatus.equals(that.employmentStatus) && address.equals(that.address) && phoneNumber.equals(that.phoneNumber) && email.equals(that.email) && bankDetails.equals(that.bankDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, title, formOfAdress, maritalStatus, dateOfBirth, employmentStatus, address, phoneNumber, email, bankDetails, dogOwner);
    }

    @Override
    public String toString() {
        return "CustomerEntity{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", title='" + title + '\'' +
                ", formOfAdress='" + formOfAdress + '\'' +
                ", maritalStatus='" + maritalStatus + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", employmentStatus='" + employmentStatus + '\'' +
                ", address=" + address +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", bankDetails='" + bankDetails + '\'' +
                ", hasDog=" + dogOwner +
                '}';
    }
}
