package EDA.BillingService.Persistence.Entity;

import event.objects.customer.CustomerEvent;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "Customer")
public class Customer implements Serializable {

    @Id
    @Column(name = "customer_id", unique = true, nullable = false)
    private Long id;

    @Column(name = "form_Of_Address", nullable = false)
    private String formOfAddress;

    @Column(name = "title")
    private String title;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "bank_details", nullable = false)
    private String bankDetails;

    public Customer() {
    }

    public Customer(Long id, String firstName, String lastName, String formOfAddress, String title, String bankDetails) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.formOfAddress = formOfAddress;
        this.title = title;
        this.bankDetails = bankDetails;
    }

    public Customer(CustomerEvent c) {
        this.id = c.getCid();
        this.formOfAddress = c.getFormOfAddress();
        this.title = c.getTitle();
        this.firstName = c.getFirstName();
        this.lastName = c.getLastName();
        this.bankDetails = c.getBankDetails();
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

    public String getFormOfAddress() {
        return formOfAddress;
    }

    public void setFormOfAddress(String formOfAddress) {
        this.formOfAddress = formOfAddress;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBankDetails() {
        return bankDetails;
    }

    public void setBankDetails(String bankDetails) {
        this.bankDetails = bankDetails;
    }
}
