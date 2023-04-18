package EDA.MeowMed.Messaging.EventObjects;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Object with select customer information
 * Can be serialized and sent as event payload
 */
public class CustomerPojo implements Serializable {
    @Serial
    private static final long serialVersionUID = 5L;

    private Long id;
    private String firstName;
    private String lastName;
    private String formOfAddress;
    private String title;
    private String maritalStatus;
    private LocalDate dateOfBirth;
    private String employmentStatus;
    private NoId_Address address;
    private String phoneNumber;
    private String email;
    private String bankDetails;

    public CustomerPojo() {
    }

    public CustomerPojo(Long id, String firstName, String lastName, String formOfAddress, String title, String maritalStatus, LocalDate dateOfBirth, String employmentStatus, NoId_Address address, String phoneNumber, String email, String bankDetails) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.formOfAddress = formOfAddress;
        this.title = title;
        this.maritalStatus = maritalStatus;
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
