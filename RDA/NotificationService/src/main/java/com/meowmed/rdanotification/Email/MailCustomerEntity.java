package com.meowmed.rdanotification.Email;

import java.time.LocalDate;

public class MailCustomerEntity {

    private String firstName;
    private String lastName;

    private String formOfAdress;
    private String martialStatus;
    private LocalDate dateOfBirth;
    private String employmentStatus;
    private MailAddressEntity adress;
    private String phoneNumber;
    private String email;
    private String bankDetails;

    public MailCustomerEntity(){

    }
    public MailCustomerEntity(String firstName, String lastName, String formOfAdress, String martialStatus, LocalDate dateOfBirth, String employmentStatus, MailAddressEntity adress, String phoneNumber, String email, String bankDetails){
        this.firstName = firstName;
        this.lastName = lastName;
        this.formOfAdress = formOfAdress;
        this.martialStatus = martialStatus;
        this.dateOfBirth = dateOfBirth;
        this.employmentStatus = employmentStatus;
        this.adress = adress;
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

    public String getFormOfAdress() {
        return formOfAdress;
    }

    public void setFormOfAdress(String formOfAdress) {
        this.formOfAdress = formOfAdress;
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

    public MailAddressEntity getAdress() {
        return adress;
    }

    public void setAdress(MailAddressEntity adress) {
        this.adress = adress;
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
