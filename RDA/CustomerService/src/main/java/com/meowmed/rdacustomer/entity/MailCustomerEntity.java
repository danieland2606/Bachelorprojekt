package com.meowmed.rdacustomer.entity;

import java.time.LocalDate;

public class MailCustomerEntity {

    private String firstName;
    private String lastName;
    private String title;
    private String formOfAdress;
    private String maritalStatus;
    private LocalDate dateOfBirth;
    private String employmentStatus;
    private String city;
    private String street;
    private int postalCode;
    private String phoneNumber;
    private String email;
    private String bankDetails;
    public MailCustomerEntity() {
    }


    public MailCustomerEntity(String firstName, String lastName, String title, String formOfAdress,
            String maritalStatus, LocalDate dateOfBirth, String employmentStatus, String city, String street,
            int postalCode, String phoneNumber, String email, String bankDetails) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
        this.formOfAdress = formOfAdress;
        this.maritalStatus = maritalStatus;
        this.dateOfBirth = dateOfBirth;
        this.employmentStatus = employmentStatus;
        this.city = city;
        this.street = street;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.bankDetails = bankDetails;
    }


    public MailCustomerEntity(CustomerRequest cEntity){
        this.firstName = cEntity.getFirstName();
        this.lastName = cEntity.getLastName();
        this.title = cEntity.getTitle();
        this.formOfAdress = cEntity.getFormOfAdress();
        this.maritalStatus = cEntity.getMaritalStatus();
        this.dateOfBirth = cEntity.getDateOfBirth();
        this.employmentStatus = cEntity.getEmploymentStatus();
        this.city = cEntity.getAddress().getCity();
        this.street = cEntity.getAddress().getStreet();
        this.postalCode = cEntity.getAddress().getPostalCode();
        this.phoneNumber = cEntity.getPhoneNumber();
        this.email = cEntity.getEmail();
        this.bankDetails = cEntity.getBankDetails();
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
    public int getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(int postalCode) {
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
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + ((formOfAdress == null) ? 0 : formOfAdress.hashCode());
        result = prime * result + ((maritalStatus == null) ? 0 : maritalStatus.hashCode());
        result = prime * result + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
        result = prime * result + ((employmentStatus == null) ? 0 : employmentStatus.hashCode());
        result = prime * result + ((city == null) ? 0 : city.hashCode());
        result = prime * result + ((street == null) ? 0 : street.hashCode());
        result = prime * result + postalCode;
        result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((bankDetails == null) ? 0 : bankDetails.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MailCustomerEntity other = (MailCustomerEntity) obj;
        if (firstName == null) {
            if (other.firstName != null)
                return false;
        } else if (!firstName.equals(other.firstName))
            return false;
        if (lastName == null) {
            if (other.lastName != null)
                return false;
        } else if (!lastName.equals(other.lastName))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        if (formOfAdress == null) {
            if (other.formOfAdress != null)
                return false;
        } else if (!formOfAdress.equals(other.formOfAdress))
            return false;
        if (maritalStatus == null) {
            if (other.maritalStatus != null)
                return false;
        } else if (!maritalStatus.equals(other.maritalStatus))
            return false;
        if (dateOfBirth == null) {
            if (other.dateOfBirth != null)
                return false;
        } else if (!dateOfBirth.equals(other.dateOfBirth))
            return false;
        if (employmentStatus == null) {
            if (other.employmentStatus != null)
                return false;
        } else if (!employmentStatus.equals(other.employmentStatus))
            return false;
        if (city == null) {
            if (other.city != null)
                return false;
        } else if (!city.equals(other.city))
            return false;
        if (street == null) {
            if (other.street != null)
                return false;
        } else if (!street.equals(other.street))
            return false;
        if (postalCode != other.postalCode)
            return false;
        if (phoneNumber == null) {
            if (other.phoneNumber != null)
                return false;
        } else if (!phoneNumber.equals(other.phoneNumber))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (bankDetails == null) {
            if (other.bankDetails != null)
                return false;
        } else if (!bankDetails.equals(other.bankDetails))
            return false;
        return true;
    }
    @Override
    public String toString() {
        return "MailCustomerEntity [firstName=" + firstName + ", lastName=" + lastName + ", title=" + title
                + ", formOfAdress=" + formOfAdress + ", martialStatus=" + maritalStatus + ", dateOfBirth=" + dateOfBirth
                + ", employmentStatus=" + employmentStatus + ", city=" + city + ", street=" + street + ", postalCode="
                + postalCode + ", phoneNumber=" + phoneNumber + ", email=" + email + ", bankDetails=" + bankDetails
                + "]";
    }


}
