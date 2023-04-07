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

    private String formofAdress;
    private String martialStatus;
    private LocalDate dateOfBirth;
    private String employmentStatus;
    @OneToOne
    private AddressEntity address;
    private String phoneNumber;
    private String email;
    private String bankDetails;

    public CustomerEntity(String firstName, String lastName, String formofAdress, String martialStatus, LocalDate dateOfBirth, String employmentStatus, AddressEntity address, String phoneNumber, String email, String bankDetails) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.formofAdress= formofAdress;
        this.martialStatus = martialStatus;
        this.dateOfBirth = dateOfBirth;
        this.employmentStatus = employmentStatus;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.bankDetails = bankDetails;
    }

    public CustomerEntity() {

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

    public String getFormofAdress() {
        return formofAdress;
    }

    public void setFormofAdress(String formofAdress) {
        this.formofAdress = formofAdress;
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







}
